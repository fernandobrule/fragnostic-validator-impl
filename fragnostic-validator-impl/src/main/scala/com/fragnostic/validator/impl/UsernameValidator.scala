package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class UsernameValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, username: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    handleShort(CONF_MIN_LENGTH, domain, params) fold (
      error => error.failureNel,
      usernameMinimumLength => handleShort(CONF_MAX_LENGTH, domain, params) fold (
        error => error.failureNel,
        usernameMaximumLength =>
          if (username.trim.isEmpty) {
            if (mandatory) {
              getFailureNel(locale, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY, messages)
            } else {
              "".successNel
            }
          } else if (username.trim.length < usernameMinimumLength) {
            getFailureNel(locale, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT, messages)
          } else if (username.trim.length > usernameMaximumLength) {
            getFailureNel(locale, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG, messages)
          } else {
            username.trim.successNel
          } //
      ) //
    )

}
