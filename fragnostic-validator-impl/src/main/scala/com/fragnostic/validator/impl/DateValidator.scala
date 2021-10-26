package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class DateValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  val datePattern = """\s*(\d{4}-\d{2}-\d{2})\s*"""

  override def validate(locale: Locale, domain: String, dateTime: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (dateTime.trim.isEmpty) {
      if (mandatory) {
        getFailureNel(DATE_VALIDATOR_DATE_IS_EMPTY, messages)
      } else {
        "".successNel
      }
    } else {

      val dateRegex = params.getOrElse("DATE_REGEX", datePattern).r

      dateTime match {
        case dateRegex(date) => s"$date".successNel
        case _ => getFailureNel(DATE_VALIDATOR_DATE_IS_NOT_VALID, messages)
      }
    }

}

