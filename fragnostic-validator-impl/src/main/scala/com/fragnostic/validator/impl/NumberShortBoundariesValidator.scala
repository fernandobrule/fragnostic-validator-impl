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
              messages.getOrElse(NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_SHORT_IS_TOO_SHORT, s"message___${NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_SHORT_IS_TOO_SHORT}___is.not.available").failureNel
            } else if (someNumber > maxValue) {
              messages.getOrElse(NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_SHORT_IS_TOO_LONG, s"message___${NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_SHORT_IS_TOO_LONG}___is.not.available").failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
