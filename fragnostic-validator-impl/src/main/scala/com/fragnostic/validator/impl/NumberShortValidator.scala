package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberShortValidator extends ValidatorApi[Short] with ValidatorSupport with TypeShortHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Short, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Short] =
    handleShort(CONF_MAX_VALUE, domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleShort(CONF_MIN_VALUE, domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              getMessage(locale, MSG_NUMBER_SHORT_VALIDATOR_NUMBER_IS_TOO_SHORT, messages).failureNel
            } else if (someNumber > maxValue) {
              getMessage(locale, MSG_NUMBER_SHORT_VALIDATOR_NUMBER_IS_TOO_LONG, messages).failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
