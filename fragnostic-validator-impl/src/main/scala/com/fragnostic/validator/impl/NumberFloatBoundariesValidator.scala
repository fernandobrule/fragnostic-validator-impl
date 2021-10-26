package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeFloatHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberFloatBoundariesValidator extends ValidatorApi[Float] with ValidatorSupport with TypeFloatHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Float, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Float] =
    handleFloat("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleFloat("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages.getOrElse(NUMBER_FLOAT_BOUNDARIES_VALIDATOR_NUMBER_FLOAT_IS_TOO_SHORT, s"message___${NUMBER_FLOAT_BOUNDARIES_VALIDATOR_NUMBER_FLOAT_IS_TOO_SHORT}___is.not.available").failureNel
            } else if (someNumber > maxValue) {
              messages.getOrElse(NUMBER_FLOAT_BOUNDARIES_VALIDATOR_NUMBER_FLOAT_IS_TOO_LONG, s"message___${NUMBER_FLOAT_BOUNDARIES_VALIDATOR_NUMBER_FLOAT_IS_TOO_LONG}___is.not.available").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
