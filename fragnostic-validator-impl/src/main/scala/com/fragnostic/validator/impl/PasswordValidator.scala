package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class PasswordValidator extends ValidatorApi[String] with ValidatorSupport {

  val passwordMinimumLength: Short = 8
  val passwordMaximumLength: Short = 64

  override def validate(password: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (!argsAreValid(numberExpected = 2, messages: _*)) {
      "password.validator.wrong.number.of.messages".failureNel
    } else if (password.trim.length < passwordMinimumLength || password.trim.length > passwordMaximumLength) {
      messages(1).failureNel
    } else if (password.trim.isEmpty) {
      messages(0).failureNel
    } else {
      password.trim.successNel
    }

}
