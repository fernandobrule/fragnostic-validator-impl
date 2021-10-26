package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberShortBoundariesValidator extends ValidatorApi[Short] with ValidatorSupport with TypeShortHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Short, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Short] =
    handleShort("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleShort("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              getMessage(NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_SHORT_IS_TOO_SHORT, messages).failureNel
            } else if (someNumber > maxValue) {
              getMessage(NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_SHORT_IS_TOO_LONG, messages).failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
