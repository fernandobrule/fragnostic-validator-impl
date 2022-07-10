package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeFloatHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberFloatBoundariesValidator extends ValidatorApi[Float] with ValidatorSupport with TypeFloatHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Float, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Float] =
    handleFloat(CONF_MAX_VALUE, domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleFloat(CONF_MIN_VALUE, domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              getMessage(locale, MSG_NUMBER_FLOAT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT, messages).failureNel
            } else if (someNumber > maxValue) {
              getMessage(locale, MSG_NUMBER_FLOAT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, messages).failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
