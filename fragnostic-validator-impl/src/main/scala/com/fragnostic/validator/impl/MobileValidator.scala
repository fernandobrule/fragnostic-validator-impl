package com.fragnostic.validator.impl

import com.fragnostic.formatter.support.MobileFormatter
import com.fragnostic.validator.api.{ Validated, ValidatorApi }
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

  private def textMaxLengthValidator = new TextMaxLengthValidator

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

  private def validateCountryCode(locale: Locale, mobile: String, hasToFormat: Boolean, params: Map[String, String], messages: List[String]): Validated[String] =
    handleList("countryCodesWhiteList", params) fold (
      error => {
        logger.error(s"validate() - $error")
        error.failureNel
      },
      countryCodesWhiteList => {
        validateCountryCode(mobile, countryCodesWhiteList, hasToFormat, getErrorMessage(locale, "mobile.validator.mobile.without.country.code", Nil, validatorI18n, 2, messages))
      } //
    ) //

  private def validateMobile(locale: Locale, rawMobile: String, params: Map[String, String], messages: List[String]): Validated[String] = {
    val notNumbers = rawMobile.filter(c => !isValid(c)).toList
    if (notNumbers.nonEmpty) {
      getErrorMessage(locale, "mobile.validator.mobile.is.not.valid", Nil, validatorI18n, 1, messages).failureNel
    } else {
      val numbers: List[Int] = rawMobile.filter(c => c.isDigit).map(c => c.asDigit).toList
      if (numbers.isEmpty) {
        getErrorMessage(locale, "mobile.validator.mobile.is.empty", Nil, validatorI18n, 0, messages).failureNel
      } else {
        val mobile: String = numbers.mkString("")

        (for {
          hasToFormat <- handleBoolean("hasToFormat", params)
          needsValidateCountryCode <- handleBoolean("validateCountryCode", params)
        } yield {
          if (needsValidateCountryCode) {
            validateCountryCode(locale, mobile, hasToFormat, params, messages)
          } else {
            hasToFormat2(mobile, hasToFormat)
          }
        }).fold(
          error => {
            logger.error(s"***validate() - $error")
            error.failureNel
          },
          ans => ans)
      }
    }
  }

  override def validate(rawMobile: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] =
    if (rawMobile.trim.isEmpty) {
      getErrorMessage(locale, "mobile.validator.mobile.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {

      textMaxLengthValidator.validate(rawMobile.trim, locale, params) fold (
        error => error.head.failureNel,
        dummy => validateMobile(locale, rawMobile, params, messages))

    }

}
