package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateTimeValidator extends ValidatorApi[String] with ValidatorSupport {

  private val dateTimePattern = """\s*(\d{4}-\d{2}-\d{2})\s*(\d{2}:\d{2}:\d{2})\s*""".r

  override def validate(dateTime: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    if (argsAreValid(numberExpected = 2, messages: _*)) {
      "date.time.validator.wrong.number.of.messages".failureNel
    } else if (dateTime.trim.isEmpty) {
      messages(0).failureNel
    } else {
      dateTime match {
        case dateTimePattern(date, time) => s"$date $time".successNel
        case _ => messages(1).failureNel
      }
    }

}

