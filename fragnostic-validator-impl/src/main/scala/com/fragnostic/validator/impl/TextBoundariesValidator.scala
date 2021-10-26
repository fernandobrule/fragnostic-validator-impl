package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class TextBoundariesValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[String] =
    if (text.trim.isEmpty) {
      if (mandatory) {
        messages.getOrElse(TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY, s"message___${TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY}___is.not.available").failureNel
      } else {
        "".successNel
      }
    } else {
      handleInt("maxLength", domain, params) fold (
        error => error.failureNel,
        maxLength =>
          handleInt("minLength", domain, params) fold (
            error => error.failureNel,
            minLength => {
              val textLength = text.length
              if (textLength < minLength) {
                messages.getOrElse(TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_SHORT, s"message___${TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_SHORT}___is.not.available").failureNel
              } else if (textLength > maxLength) {
                messages.getOrElse(TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_LONG, s"message___${TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_LONG}___is.not.available").failureNel
              } else {
                text.trim.successNel
              }
            } //
          ) //
      )
    }

}
