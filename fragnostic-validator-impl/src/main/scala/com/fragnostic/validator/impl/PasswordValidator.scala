package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class PasswordValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler {

  override def validate(locale: Locale, domain: String, password: String, params: Map[String, String], messages: List[String], mandatory: Boolean = true): Validated[String] = {
    // TODO esta es una implementación absolutamente mínima
    (for {
      minLength <- handleShort("minLength", domain, params)
      maxLength <- handleShort("maxLength", domain, params)
    } yield {
      if (password.trim.isEmpty) {
        getErrorMessage(locale, "password.validator.password.is.empty", Nil, validatorI18n, idxTextEmpty, messages)
      } else if (password.trim.length < minLength || password.trim.length > maxLength) {
        getErrorMessage(locale, "password.validator.password.length.is.not.valid", List(password.trim.length.toString, minLength.toString, maxLength.toString), validatorI18n, idxTextLengthier, messages)
      } else {
        password.trim
      }
    }) fold (
      error => error.failureNel,
      validated => validated.successNel)
  }

}
