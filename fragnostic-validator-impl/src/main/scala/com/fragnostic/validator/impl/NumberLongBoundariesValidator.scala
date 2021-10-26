package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeLongHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberLongBoundariesValidator extends ValidatorApi[Long] with ValidatorSupport with TypeLongHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Long, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Long] =
    handleLong("maxValue", domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleLong("minValue", domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              messages.getOrElse(NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT, s"message___${NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT}___is.not.available").failureNel
            } else if (someNumber > maxValue) {
              messages.getOrElse(NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG, s"message___${NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG}___is.not.available").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
