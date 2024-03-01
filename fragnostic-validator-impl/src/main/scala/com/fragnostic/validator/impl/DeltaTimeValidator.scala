package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale
import scala.util.matching.Regex

class DeltaTimeValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  private val yearsPattern: Regex = """(\d+)y""".r
  private val yearsMonthsPattern: Regex = """(\d+)y (\d+)m""".r
  private val yearsMonthsWeeksPattern: Regex = """(\d+)y (\d+)m (\d+)w""".r
  private val monthsPattern: Regex = """(\d+)m""".r
  private val weeksPattern: Regex = """(\d+)w""".r
  private val daysPattern: Regex = """(\d+)d""".r

  private val stringValidator = new StringValidator

  private val deltaTimeValidatorParams: Map[String, String] = Map(
    CONF_MIN_LENGTH -> "2",
    CONF_MAX_LENGTH -> "18" //
  )

  override def validate(locale: Locale, domain: String, deltaTime: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    stringValidator.validate(locale, domain, deltaTime, deltaTimeValidatorParams, messages, mandatory) fold (
      nel => nel.head.failureNel,
      {
        case deltaTime @ yearsPattern(years) => deltaTime.successNel
        case deltaTime @ yearsMonthsPattern(years, months) => deltaTime.successNel
        case deltaTime @ yearsMonthsWeeksPattern(years, months, weeks) => deltaTime.successNel
        case deltaTime @ monthsPattern(months) => deltaTime.successNel
        case deltaTime @ weeksPattern(weeks) => deltaTime.successNel
        case deltaTime @ daysPattern(days) => deltaTime.successNel
        case _ => getFailureNel(locale, domain, MSG_DELTA_TIME_VALIDATOR_DELTA_TIME_IS_NOT_VALID, messages)
      } //
    )

  }
}
