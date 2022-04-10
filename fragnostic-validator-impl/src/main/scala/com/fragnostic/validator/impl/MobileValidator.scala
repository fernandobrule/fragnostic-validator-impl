package com.fragnostic.validator.impl

import com.fragnostic.formatter.support.MobileFormatter
import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeBooleanHandler, TypeListHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class MobileValidator extends ValidatorApi[String] with ValidatorSupport with MobileFormatter with TypeBooleanHandler with TypeListHandler with ValidatorMessagesKeys {

  private def textBoundariesValidator = new TextBoundariesValidator

  private def textBoundariesValidatorMessages(messages: Map[String, String]): Map[String, String] = Map(
    TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> getMessage(MOBILE_VALIDATOR_MOBILE_IS_EMPTY, messages),
    TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT -> getMessage(MOBILE_VALIDATOR_MOBILE_IS_TOO_SHORT, messages),
    TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> getMessage(MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG, messages) //
  )

  private val validChars = List(
    '\u002d',
    '\u002b',
    '\u0020',
    '\u0028',
    '\u0029' //
  )

  private def isValid(c: Char): Boolean =
    c.isDigit || validChars.contains(c)

  private def hasToFormatApply(mobile: String, hasToFormat: Boolean): Validated[String] =
    if (hasToFormat) {
      format(mobile).successNel
    } else {
      mobile.successNel
    }

  private def validateCountryCode(mobile: String, countryCodesWhiteList: List[String], hasToFormat: Boolean, errorMessage: String): Validated[String] = {
    val code: String = mobile.substring(0, 2)
    if (countryCodesWhiteList.contains(code)) {
      hasToFormatApply(mobile, hasToFormat)
    } else {
      errorMessage.failureNel
    }
  }

  private def validateCountryCode(locale: Locale, mobile: String, hasToFormat: Boolean, domain: String, params: Map[String, String], messages: Map[String, String]): Validated[String] =
    handleList("countryCodesWhiteList", domain, params) fold (
      error => error.failureNel,
      countryCodesWhiteList => {
        validateCountryCode(mobile, countryCodesWhiteList, hasToFormat, messages(MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE))
      } //
    ) //

  override def validate(locale: Locale, domain: String, rawMobile: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    val notNumbers = rawMobile.filter(c => !isValid(c)).toList
    if (notNumbers.nonEmpty) {
      messages(MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID).failureNel
    } else {
      val numbers: List[Int] = rawMobile.filter(c => c.isDigit).map(c => c.asDigit).toList
      if (numbers.isEmpty) {
        if (mandatory) {
          messages(MOBILE_VALIDATOR_MOBILE_IS_EMPTY).failureNel
        } else {
          "".successNel
        }
      } else {
        val mobile = numbers.mkString("")
        textBoundariesValidator.validate(locale, domain, mobile, params, textBoundariesValidatorMessages(messages), mandatory) fold (
          error => error.head.failureNel,
          mobile => {
            (for {
              hasToFormat <- handleBoolean("hasToFormat", domain, params)
              needsValidateCountryCode <- handleBoolean("validateCountryCode", domain, params)
            } yield {
              if (needsValidateCountryCode) {
                validateCountryCode(locale, mobile, hasToFormat, domain, params, messages)
              } else {
                hasToFormatApply(mobile, hasToFormat)
              }
            }).fold(
              error => error.failureNel,
              ans => ans //
            )
          })
      }
    }
  }

}
