package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeLongHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberLongBoundariesValidator extends ValidatorApi[Long] with ValidatorSupport with TypeLongHandler {

  override def validate(locale: Locale, domain: String, someNumber: Long, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Long] =
    handleLong("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleLong("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages("number.long.boundaries.validator.number.is.too.short").failureNel
            } else if (someNumber > maxValue) {
              messages("number.long.boundaries.validator.number.is.too.long").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
