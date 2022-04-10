package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateTimeValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  // TODO generalizar
  private val dateTimePattern = """\s*(\d{4}-\d{2}-\d{2})\s*(\d{2}:\d{2}:\d{2})\s*"""

  override def validate(locale: Locale, domain: String, dateTime: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    Option(dateTime) match {
      case None => getFailureNel(DATE_TIME_VALIDATOR_DATE_TIME_IS_NULL, messages)
      case Some(dateTime) =>
        if (dateTime.trim.isEmpty) {
          if (mandatory) {
            getFailureNel(DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY, messages)
          } else {
            "".successNel
          }
        } else {

          val dateTimeRegex = params.getOrElse("DATE_TIME_REGEX", dateTimePattern).r

          dateTime match {
            case dateTimeRegex(date, time) => s"$date $time".successNel
            case _ => getFailureNel(DATE_TIME_VALIDATOR_DATE_TIME_IS_NOT_VALID, messages)
          }
        }
    }

}

