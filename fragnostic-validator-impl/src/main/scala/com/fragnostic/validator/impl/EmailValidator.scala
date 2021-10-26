package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class EmailValidator extends ValidatorApi[String] with ValidatorSupport with EmailValidatorByRfc2822Validator with ValidatorMessagesKeys {

  private def textBoundariesValidator = new TextBoundariesValidator

  private def textBoundariesValidatorMessages(messages: Map[String, String]): Map[String, String] = Map(
    TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> messages.getOrElse(EMAIL_VALIDATOR_EMAIL_IS_EMPTY, s"message___${EMAIL_VALIDATOR_EMAIL_IS_EMPTY}___is.not.available"),
    TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_SHORT -> messages.getOrElse(EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT, s"message___${EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT}___is.not.available"),
    TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_LONG -> messages.getOrElse(EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG, s"message___${EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG}___is.not.available") //
  )

  override def validate(locale: Locale, domain: String, email: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    textBoundariesValidator.validate(locale, domain, email, params, textBoundariesValidatorMessages(messages), mandatory) fold (
      error => error.head.failureNel,
      email =>
        if (!mandatory && email.trim.isEmpty) {
          "".successNel
        } else {
          validateByRfc2822Validator(email, messages.getOrElse(EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID, s"message___${EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID}___is.not.available"))
        } //
    )

}
