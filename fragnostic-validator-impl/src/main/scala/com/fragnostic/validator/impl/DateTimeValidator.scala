package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateTimeValidator extends ValidatorApi[String] with ValidatorSupport {

  private val dateTimePattern = """\s*(\d{4}-\d{2}-\d{2})\s*(\d{2}:\d{2}:\d{2})\s*""".r

  override def validate(locale: Locale, domain: String, dateTime: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (dateTime.trim.isEmpty) {
      if (mandatory) {
        messages("date.time.validator.date.time.is.empty").failureNel
      } else {
        "".successNel
      }
    } else {
      dateTime match {
        case dateTimePattern(date, time) => s"$date $time".successNel
        case _ => messages("date.time.validator.date.time.is.not.valid").failureNel
      }
    }

}

