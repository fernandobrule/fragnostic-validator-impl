package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UsernameValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, username: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    handleShort("minLength", domain, params) fold (
      error => error.failureNel,
      usernameMinimumLength => handleShort("maxLength", domain, params) fold (
        error => error.failureNel,
        usernameMaximumLength =>
          if (username.trim.isEmpty) {
            if (mandatory) {
              messages.getOrElse(USERNAME_VALIDATOR_USERNAME_IS_EMPTY, s"message___${USERNAME_VALIDATOR_USERNAME_IS_EMPTY}___is.not.available").failureNel
            } else {
              "".successNel
            }
          } else if (username.trim.length < usernameMinimumLength) {
            messages.getOrElse(USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT, s"message___${USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT}___is.not.available").failureNel
          } else if (username.trim.length > usernameMaximumLength) {
            messages.getOrElse(USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG, s"message___${USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG}___is.not.available").failureNel
          } else {
            username.trim.successNel
          } //
      ) //
    )

}
