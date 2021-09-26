package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateValidator extends ValidatorApi[String] with ValidatorSupport {

  val DATE_REGEX = "DATE_REGEX"

  override def validate(locale: Locale, domain: String, dateTime: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (dateTime.trim.isEmpty) {
      if (mandatory) {
        messages("date.validator.date.is.empty").failureNel
      } else {
        "".successNel
      }
    } else {

      val dateRegex = params.getOrElse(DATE_REGEX, """\s*(\d{4}-\d{2}-\d{2})\s*""").r

      dateTime match {
        case dateRegex(date) => s"$date".successNel
        case _ => messages("date.validator.date.is.not.valid").failureNel
      }
    }

}

