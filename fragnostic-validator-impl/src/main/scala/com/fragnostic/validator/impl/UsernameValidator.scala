package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UsernameValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler {

  override def validate(locale: Locale, domain: String, username: String, params: Map[String, String], messages: List[String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    (for {
      usernameMinimumLength <- handleShort("usernameMinimumLength", domain, params)
      usernameMaximumLength <- handleShort("usernameMaximumLength", domain, params)
    } yield {
      if (username.trim.isEmpty) {
        getErrorMessage(locale, "username.validator.username.is.empty", Nil, validatorI18n, idxTextEmpty, messages)
      } else if (username.trim.length < usernameMinimumLength || username.trim.length > usernameMaximumLength) {
        getErrorMessage(locale, "username.validator.username.length.is.not.valid", List(username.trim.length.toString, usernameMinimumLength.toString, usernameMaximumLength.toString), validatorI18n, idxTextNotValid, messages)
      } else {
        username.trim
      }
    }) fold (
      error => error.failureNel,
      validated => validated.successNel)

}
