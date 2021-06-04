package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class PasswordValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler {

  override def validate(password: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] = {
    // TODO esta es una implementación absolutamente mínima
    (for {
      passwordMinimumLength <- handleShort("passwordMinimumLength", params)
      passwordMaximumLength <- handleShort("passwordMaximumLength", params)
    } yield {
      if (password.trim.isEmpty) {
        getErrorMessage(locale, "password.validator.password.is.empty", Nil, validatorI18n, 0, messages)
      } else if (password.trim.length < passwordMinimumLength || password.trim.length > passwordMaximumLength) {
        getErrorMessage(locale, "password.validator.password.length.is.not.valid", List(password.trim.length.toString, passwordMinimumLength.toString, passwordMaximumLength.toString), validatorI18n, 1, messages)
      } else {
        password.trim
      }
    }) fold (
      error => error.failureNel,
      validated => validated.successNel)
  }

}
