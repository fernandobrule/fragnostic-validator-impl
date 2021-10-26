package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberIntBoundariesValidator extends ValidatorApi[Int] with ValidatorSupport with TypeIntHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Int, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Int] =
    handleInt("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleInt("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages.getOrElse(NUMBER_INT_BOUNDARIES_VALIDATOR_NUMBER_INT_IS_TOO_SHORT, s"message___${NUMBER_INT_BOUNDARIES_VALIDATOR_NUMBER_INT_IS_TOO_SHORT}___is.not.available").failureNel
            } else if (someNumber > maxValue) {
              messages.getOrElse(NUMBER_INT_BOUNDARIES_VALIDATOR_NUMBER_INT_IS_TOO_LONG, s"message___${NUMBER_INT_BOUNDARIES_VALIDATOR_NUMBER_INT_IS_TOO_LONG}___is.not.available").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
