package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.util.Try

class DateValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  private val textValidator = new TextValidator

  override def validate(locale: Locale, domain: String, date: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    textValidator.validate(locale, domain, date, params, messages, mandatory) fold (
      nel => nel.head.failureNel,
      date => {
        if (date.isEmpty) {
          "".successNel
        } else {
          if (!params.contains(CONF_DATE_FORMAT)) {
            getMessage(locale, domain, MSG_DATE_VALIDATOR_YOU_HAVE_NOT_ENTERED_DATE_FORMAT, messages).failureNel
          } else {
            val dateFormat = params(CONF_DATE_FORMAT)
            val dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
            Try(LocalDate.parse(date, dateTimeFormatter)) fold (
              error => getFailureNel(locale, domain, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, messages),
              jdate => s"$date".successNel //
            )
          }
        } //
      } //
    )
  }

}

