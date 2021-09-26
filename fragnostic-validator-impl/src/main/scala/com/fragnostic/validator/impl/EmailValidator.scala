package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class EmailValidator extends ValidatorApi[String] with ValidatorSupport with EmailValidatorByRfc2822Validator {

  private def textBoundariesValidator = new TextBoundariesValidator

  override def validate(locale: Locale, domain: String, email: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    textBoundariesValidator.validate(locale, domain, email, params, messages, mandatory) fold (
      error => error.head.failureNel,
      email =>
        if (!mandatory && email.trim.isEmpty) {
          "".successNel
        } else {
          validateByRfc2822Validator(email, messages("email.validator.email.is.not.valid"))
        } //
    )

}
