package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class UsernameValidator extends ValidatorApi[String] with ValidatorSupport {

  val usernameMinimumLength: Short = 8
  val usernameMaximumLength: Short = 254

  override def validate(username: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (!argsAreValid(numberExpected = 2, messages: _*)) {
      "username.validator.wrong.number.of.messages".failureNel
    } else if (username.trim.isEmpty) {
      messages(0).failureNel
    } else if (username.trim.length < usernameMinimumLength || username.trim.length > usernameMaximumLength) {
      messages(1).failureNel
    } else {
      username.trim.successNel
    }

}
