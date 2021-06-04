package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateTimeValidator extends ValidatorApi[String] with ValidatorSupport {

  private val dateTimePattern = """\s*(\d{4}-\d{2}-\d{2})\s*(\d{2}:\d{2}:\d{2})\s*""".r

  override def validate(dateTime: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] =
    if (dateTime.trim.isEmpty) {
      getErrorMessage(locale, "date.time.validator.date.time.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {
      dateTime match {
        case dateTimePattern(date, time) => s"$date $time".successNel
        case _ => getErrorMessage(locale, "date.time.validator.date.time.is.not.valid", Nil, validatorI18n, 1, messages).failureNel
      }
    }

}

