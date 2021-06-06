package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class EmailValidator extends ValidatorApi[String] with ValidatorSupport with EmailValidatorByRfc2822Validator {

  private def textMaxLengthValidator = new TextMaxLengthValidator

  override def validate(email: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] = {
    if (email.trim.isEmpty) {
      getErrorMessage(locale, "email.validator.email.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {

      textMaxLengthValidator.validate(email, locale, params, messages) fold (
        error => error.head.failureNel,
        email => validateByRfc2822Validator(email, getErrorMessage(locale, "email.validator.email.is.not.valid", Nil, validatorI18n, 1, messages)) //
      )

    }
  }

}
