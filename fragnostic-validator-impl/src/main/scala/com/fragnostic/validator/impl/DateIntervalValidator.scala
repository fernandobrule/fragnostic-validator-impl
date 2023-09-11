package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._
import scalaz.{ Failure, Success }

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateIntervalValidator extends ValidatorApi[(String, String)] with ValidatorSupport with ValidatorMessagesKeys {

  private val dateValidator = new DateValidator

  override def validate(locale: Locale, domain: String, dateInterval: (String, String), params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[(String, String)] = {

    val domains = domain.split(";") match {
      case Array(start, end) => (start, end)
      case _ => ("start?", "end?")
    }

    val domainStart = domains._1
    val domainEnd = domains._2

    val msgStart: Map[String, String] = Map(
      MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID -> messages.getOrElse(s"${MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID}_$domainStart", MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID),
      MSG_TEXT_VALIDATOR_TEXT_IS_NULL -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_NULL}_$domainStart", MSG_TEXT_VALIDATOR_TEXT_IS_NULL),
      MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY}_$domainStart", MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY),
      MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT}_$domainStart", MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT),
      MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG}_$domainStart", MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG) //
    )

    val msgEnd: Map[String, String] = Map(
      MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID -> messages.getOrElse(s"${MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID}_$domainEnd", MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID),
      MSG_TEXT_VALIDATOR_TEXT_IS_NULL -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_NULL}_$domainEnd", MSG_TEXT_VALIDATOR_TEXT_IS_NULL),
      MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY}_$domainEnd", MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY),
      MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT}_$domainEnd", MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT),
      MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG -> messages.getOrElse(s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG}_$domainEnd", MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG) //
    )

    val validated = (dateValidator.validate(locale, domainStart, dateInterval._1, params, msgStart, mandatory)
      |@| dateValidator.validate(locale, domainEnd, dateInterval._2, params, msgEnd, mandatory)) {
        (dateStart, dateEnd) => (dateStart, dateEnd)
      }

    validated match {
      case Failure(e) =>
        validated
      case Success(tuple) =>
        if (!params.contains(CONF_DATE_FORMAT)) {
          getMessage(locale, domain, MSG_DATE_VALIDATOR_YOU_HAVE_NOT_ENTERED_DATE_FORMAT, messages).failureNel
        } else {
          val dateFormat = params(CONF_DATE_FORMAT)
          val dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
          val start = LocalDate.parse(tuple._1, dateTimeFormatter)
          val end = LocalDate.parse(tuple._2, dateTimeFormatter)
          if (start.isAfter(end)) {
            getMessage(locale, domain, MSG_DATE_INTERVAL_VALIDATOR_START_DATE_IS_AFTER_END_DATE, messages).failureNel
          } else {
            tuple.successNel
          }
        }
    }

  }

}
