package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeLongHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberLongBoundariesValidator extends ValidatorApi[Long] with ValidatorSupport with TypeLongHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Long, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Long] =
    handleLong(CONF_MAX_VALUE, domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleLong(CONF_MIN_VALUE, domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              getMessage(locale, MSG_NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT, messages).failureNel
            } else if (someNumber > maxValue) {
              getMessage(locale, MSG_NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, messages).failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
