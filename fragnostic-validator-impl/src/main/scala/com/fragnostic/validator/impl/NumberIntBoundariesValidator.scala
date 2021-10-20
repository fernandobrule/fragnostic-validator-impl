package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberIntBoundariesValidator extends ValidatorApi[Int] with ValidatorSupport with TypeIntHandler {

  override def validate(locale: Locale, domain: String, someNumber: Int, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Int] =
    handleInt("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleInt("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages("number.int.boundaries.validator.number.is.too.short").failureNel
            } else if (someNumber > maxValue) {
              messages("number.int.boundaries.validator.number.is.too.long").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
