package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberShortBoundariesValidator extends ValidatorApi[Short] with ValidatorSupport with TypeShortHandler {

  override def validate(locale: Locale, domain: String, someNumber: Short, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Short] =
    handleShort("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleShort("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages("number.short.boundaries.validator.number.is.too.short").failureNel
            } else if (someNumber > maxValue) {
              messages("number.short.boundaries.validator.number.is.too.long").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
