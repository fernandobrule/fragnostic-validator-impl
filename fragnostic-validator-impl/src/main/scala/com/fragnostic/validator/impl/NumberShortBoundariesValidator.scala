package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api.{ VALIDATOR_TEXT_TOO_LONG, VALIDATOR_TEXT_TOO_SHORT, Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberShortBoundariesValidator extends ValidatorApi[Short] with ValidatorSupport with TypeShortHandler {

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, someNumber: Short, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Short] =
    handleShort("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleShort("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              getErrorMessage(locale, "number.float.boundaries.validator.number.is.too.short", List(domain, someNumber.toString, minValue.toString), validatorI18n, VALIDATOR_TEXT_TOO_SHORT, messages).failureNel
            } else if (someNumber > maxValue) {
              getErrorMessage(locale, "number.float.boundaries.validator.number.is.too.long", List(domain, someNumber.toString, maxValue.toString), validatorI18n, VALIDATOR_TEXT_TOO_LONG, messages).failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
