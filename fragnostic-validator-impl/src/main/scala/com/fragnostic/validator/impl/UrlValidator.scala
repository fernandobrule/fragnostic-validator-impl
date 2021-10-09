package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeBooleanHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UrlValidator extends ValidatorApi[String] with ValidatorSupport with TypeBooleanHandler {

  // TODO still in doubt
  private val urlPattern = """^([w{3}.|https://|ftp://|file://|http://][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])""".r

  private def urlFormat(url: String): String = url

  override def validate(locale: Locale, domain: String, url: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (url.trim.isEmpty) {
      if (mandatory) {
        messages("url.validator.url.is.empty").failureNel
      } else {
        "".successNel
      }
    } else {
      handleBoolean("hasToFormat", domain, params) fold (
        error => error.failureNel,
        hasToFormat =>
          url.trim match {
            case urlPattern(url) => if (hasToFormat) urlFormat(url).successNel else url.successNel
            case _ => messages("url.validator.url.is.not.valid").failureNel
          } //
      )
    }

}
