package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class TextBoundariesValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[String] =
    if (text.trim.isEmpty) {
      if (mandatory) {
        messages("text.boundaries.validator.text.is.empty").failureNel
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
                messages("text.boundaries.validator.text.is.too.short").failureNel
              } else if (textLength > maxLength) {
                messages("text.boundaries.validator.text.is.too.long").failureNel
              } else {
                text.trim.successNel
              }
            } //
          ) //
      )
    }

}
