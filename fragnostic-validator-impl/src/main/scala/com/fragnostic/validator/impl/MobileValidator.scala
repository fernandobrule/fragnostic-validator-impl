package com.fragnostic.validator.impl

import com.fragnostic.formatter.support.MobileFormatter
import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeBooleanHandler, TypeListHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class MobileValidator extends ValidatorApi[String] with ValidatorSupport with MobileFormatter with TypeBooleanHandler with TypeListHandler with ValidatorMessagesKeys {

  private def textValidator = new TextValidator

  private def textValidatorMessages(locale: Locale, domain: String, messages: Map[String, String]): Map[String, String] = Map(
    MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY -> getMessage(locale, domain, MSG_MOBILE_VALIDATOR_MOBILE_IS_EMPTY, messages),
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT -> getMessage(locale, domain, MSG_MOBILE_VALIDATOR_MOBILE_IS_TOO_SHORT, messages),
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG -> getMessage(locale, domain, MSG_MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG, messages) //
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
      formatMobile(mobile).successNel
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
    handleList(CONF_COUNTRY_CODES_WHITE_LIST, domain, params) fold (
      error => error.failureNel,
      countryCodesWhiteList => {
        validateCountryCode(mobile, countryCodesWhiteList, hasToFormat, messages(MSG_MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE))
      } //
    ) //

  override def validate(locale: Locale, domain: String, rawMobile: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    val notNumbers = rawMobile.filter(c => !isValid(c)).toList
    if (notNumbers.nonEmpty) {
      messages(MSG_MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID).failureNel
    } else {
      val numbers: List[Int] = rawMobile.filter(c => c.isDigit).map(c => c.asDigit).toList
      if (numbers.isEmpty) {
        if (mandatory) {
          messages(MSG_MOBILE_VALIDATOR_MOBILE_IS_EMPTY).failureNel
        } else {
          "".successNel
        }
      } else {
        val mobile = numbers.mkString("")
        textValidator.validate(locale, domain, mobile, params, textValidatorMessages(locale, domain, messages), mandatory) fold (
          error => error.head.failureNel,
          mobile => {
            (for {
              hasToFormat <- handleBoolean(CONF_HAS_TO_FORMAT, domain, params)
              needsValidateCountryCode <- handleBoolean(CONF_VALIDATE_COUNTRY_CODE, domain, params)
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
