package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeFloatHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberFloatBoundariesValidator extends ValidatorApi[Float] with ValidatorSupport with TypeFloatHandler {

  override def validate(locale: Locale, domain: String, someNumber: Float, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Float] =
    handleFloat("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleFloat("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages("number.float.boundaries.validator.number.is.too.short").failureNel
            } else if (someNumber > maxValue) {
              messages("number.float.boundaries.validator.number.is.too.long").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
