package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UsernameValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler {

  override def validate(locale: Locale, domain: String, username: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    handleShort("minLength", domain, params) fold (
      error => error.failureNel,
      usernameMinimumLength => handleShort("maxLength", domain, params) fold (
        error => error.failureNel,
        usernameMaximumLength =>
          if (username.trim.isEmpty) {
            if (mandatory) {
              messages("username.validator.username.is.empty").failureNel
            } else {
              "".successNel
            }
          } else if (username.trim.length < usernameMinimumLength) {
            messages("username.validator.username.is.too.short").failureNel
          } else if (username.trim.length > usernameMaximumLength) {
            messages("username.validator.username.is.too.long").failureNel
          } else {
            username.trim.successNel
          } //
      ) //
    )

}
