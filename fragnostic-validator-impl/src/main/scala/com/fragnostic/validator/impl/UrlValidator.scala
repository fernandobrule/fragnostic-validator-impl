package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeBooleanHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UrlValidator extends ValidatorApi[String] with ValidatorSupport with TypeBooleanHandler with ValidatorMessagesKeys {

  private val urlPattern = """^([w{3}.|https://|ftp://|file://|http://][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])"""

  private def urlFormat(url: String): String = url

  override def validate(locale: Locale, domain: String, url: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (url.trim.isEmpty) {
      if (mandatory) {
        messages.getOrElse(URL_VALIDATOR_URL_IS_EMPTY, s"message___${URL_VALIDATOR_URL_IS_EMPTY}___is.not.available").failureNel
      } else {
        "".successNel
      }
    } else {
      handleBoolean("hasToFormat", domain, params) fold (
        error => error.failureNel,
        hasToFormat => {

          val urlRegex = params.getOrElse("URL_REGEX", urlPattern).r

          url.trim match {
            case urlRegex(url) => if (hasToFormat) urlFormat(url).successNel else url.successNel
            case _ => messages.getOrElse(URL_VALIDATOR_URL_IS_NOT_VALID, s"message___${URL_VALIDATOR_URL_IS_NOT_VALID}___is.not.available").failureNel
          }
        } //
      )
    }

}
