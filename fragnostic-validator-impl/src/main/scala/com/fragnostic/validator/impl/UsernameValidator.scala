package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UsernameValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler {

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, username: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    handleShort("minLength", domain, params) fold (
      error => error.failureNel,
      usernameMinimumLength => handleShort("maxLength", domain, params) fold (
        error => error.failureNel,
        usernameMaximumLength =>
          if (username.trim.isEmpty) {
            if (mandatory) {
              getErrorMessage(locale, "username.validator.username.is.empty", Nil, validatorI18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
            } else {
              "".successNel
            }
          } else if (username.trim.length < usernameMinimumLength) {
            getErrorMessage(locale, "username.validator.username.is.too.short", List(username.trim.length.toString, usernameMinimumLength.toString, usernameMaximumLength.toString), validatorI18n, VALIDATOR_TEXT_TOO_SHORT, messages).failureNel
          } else if (username.trim.length > usernameMaximumLength) {
            getErrorMessage(locale, "username.validator.username.is.too.long", List(username.trim.length.toString, usernameMaximumLength.toString, usernameMaximumLength.toString), validatorI18n, VALIDATOR_TEXT_TOO_LONG, messages).failureNel
          } else {
            username.trim.successNel
          } //
      ) //
    )

}
