package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class EmailValidator extends ValidatorApi[String] with ValidatorSupport with EmailValidatorByRfc2822Validator with ValidatorMessagesKeys {

  private def textValidator = new TextValidator

  private def textValidatorMessages(locale: Locale, domain: String, messages: Map[String, String]): Map[String, String] = Map(
    MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY -> getMessage(locale, domain, MSG_EMAIL_VALIDATOR_EMAIL_IS_EMPTY, messages),
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT -> getMessage(locale, domain, MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT, messages),
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG -> getMessage(locale, domain, MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG, messages) //
  )

  override def validate(locale: Locale, domain: String, email: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    textValidator.validate(locale, domain, email, params, textValidatorMessages(locale, domain, messages), mandatory) fold (
      error => error.head.failureNel,
      email =>
        if (!mandatory && email.trim.isEmpty) {
          "".successNel
        } else {
          validateByRfc2822Validator(email, getMessage(locale, domain, MSG_EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID, messages))
        } //
    )

}
