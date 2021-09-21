package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class TextBoundariesValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler {

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[String] =
    if (text.trim.isEmpty) {
      if (mandatory) {
        getErrorMessage(locale, "text.boundaries.validator.text.is.empty", List(domain), i18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
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
                getErrorMessage(locale, "text.boundaries.validator.text.is.too.short", List(domain, textLength.toString, minLength.toString), i18n, VALIDATOR_TEXT_TOO_SHORT, messages).failureNel
              } else if (textLength > maxLength) {
                getErrorMessage(locale, "text.boundaries.validator.text.is.too.long", List(domain, textLength.toString, maxLength.toString), i18n, VALIDATOR_TEXT_TOO_LONG, messages).failureNel
              } else {
                text.trim.successNel
              }
            } //
          ) //
      )
    }

}
