package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateValidator extends ValidatorApi[String] with ValidatorSupport {

  private val dateTimePattern = """\s*(\d{4}-\d{2}-\d{2})\s*""".r

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, dateTime: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (dateTime.trim.isEmpty) {
      if (mandatory) {
        getErrorMessage(locale, "date.validator.date.is.empty", Nil, validatorI18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
      } else {
        "".successNel
      }
    } else {
      dateTime match {
        case dateTimePattern(date) => s"$date".successNel
        case _ => getErrorMessage(locale, "date.validator.date.is.not.valid", Nil, validatorI18n, VALIDATOR_TEXT_NOT_VALID, messages).failureNel
      }
    }

}

