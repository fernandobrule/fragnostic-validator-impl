package com.fragnostic.validator.impl

import com.fragnostic.formatter.support.MobileFormatter
import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.{ TypeBooleanHandler, TypeListHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class MobileValidator extends ValidatorApi[String] with ValidatorSupport with MobileFormatter with TypeBooleanHandler with TypeListHandler {

  private[this] val logger: Logger = LoggerFactory.getLogger("MobileValidator")

  private val validChars = List(
    '\u002d',
    '\u002b',
    '\u0020',
    '\u0028',
    '\u0029')

  private def textBoundariesValidator = new TextBoundariesValidator

  private def isValid(c: Char): Boolean =
    c.isDigit || validChars.contains(c)

  private def hasToFormat2(mobile: String, hasToFormat: Boolean): Validated[String] =
    if (hasToFormat) {
      format(mobile).successNel
    } else {
      mobile.successNel
    }

  private def validateCountryCode(mobile: String, countryCodesWhiteList: List[String], hasToFormat: Boolean, errorMessage: String): Validated[String] = {
    val code: String = mobile.substring(0, 2)
    if (countryCodesWhiteList.contains(code)) {
      hasToFormat2(mobile, hasToFormat)
    } else {
      errorMessage.failureNel
    }
  }

  private def validateCountryCode(locale: Locale, mobile: String, hasToFormat: Boolean, domain: String, params: Map[String, String], messages: Map[String, String]): Validated[String] =
    handleList("countryCodesWhiteList", domain, params) fold (
      error => {
        logger.error(s"validate() - $error")
        error.failureNel
      },
      countryCodesWhiteList => {
        validateCountryCode(mobile, countryCodesWhiteList, hasToFormat, getErrorMessage(locale, "mobile.validator.mobile.without.country.code", Nil, validatorI18n, VALIDATOR_COUNTRY_CODE, messages))
      } //
    ) //

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, rawMobile: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    val notNumbers = rawMobile.filter(c => !isValid(c)).toList
    if (notNumbers.nonEmpty) {
      getErrorMessage(locale, "mobile.validator.mobile.is.not.valid", Nil, validatorI18n, VALIDATOR_TEXT_NOT_VALID, messages).failureNel
    } else {
      val numbers: List[Int] = rawMobile.filter(c => c.isDigit).map(c => c.asDigit).toList
      if (numbers.isEmpty) {
        if (mandatory) {
          getErrorMessage(locale, "mobile.validator.mobile.is.empty", Nil, validatorI18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
        } else {
          "".successNel
        }
      } else {
        val mobile = numbers.mkString("")
        textBoundariesValidator.validate(locale, i18n, domain, mobile, params, messages, mandatory) fold (
          error => error.head.failureNel,
          mobile => {
            (for {
              hasToFormat <- handleBoolean("hasToFormat", domain, params)
              needsValidateCountryCode <- handleBoolean("validateCountryCode", domain, params)
            } yield {
              if (needsValidateCountryCode) {
                validateCountryCode(locale, mobile, hasToFormat, domain, params, messages)
              } else {
                hasToFormat2(mobile, hasToFormat)
              }
            }).fold(
              error => {
                logger.error(s"validate() - $error")
                error.failureNel
              },
              ans => ans //
            )
          })
      }
    }
  }

}
