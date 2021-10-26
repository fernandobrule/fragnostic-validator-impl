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
        getFailureNel(TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY, messages)
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
                getFailureNel(TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_SHORT, messages)
              } else if (textLength > maxLength) {
                getFailureNel(TEXT_BOUNDARIES_VALIDATOR_TEXT_BOUNDARIES_IS_TOO_LONG, messages)
              } else {
                text.trim.successNel
              }
            } //
          ) //
      )
    }

}
