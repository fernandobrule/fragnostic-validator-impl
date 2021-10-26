package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class EmailValidator extends ValidatorApi[String] with ValidatorSupport with EmailValidatorByRfc2822Validator with ValidatorMessagesKeys {

  private def textBoundariesValidator = new TextBoundariesValidator

  private def textBoundariesValidatorMessages(messages: Map[String, String]): Map[String, String] = Map(
    TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> getMessage(EMAIL_VALIDATOR_EMAIL_IS_EMPTY, messages),
    TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_SHORT -> getMessage(EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT, messages),
    TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_LONG -> getMessage(EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG, messages) //
  )

  override def validate(locale: Locale, domain: String, email: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    textBoundariesValidator.validate(locale, domain, email, params, textBoundariesValidatorMessages(messages), mandatory) fold (
      error => error.head.failureNel,
      email =>
        if (!mandatory && email.trim.isEmpty) {
          "".successNel
        } else {
          validateByRfc2822Validator(email, getMessage(EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID, messages))
        } //
    )

}
