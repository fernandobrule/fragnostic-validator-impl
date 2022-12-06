package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberIntValidator extends ValidatorApi[Int] with ValidatorSupport with TypeIntHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: Int, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[Int] =
    handleInt(CONF_MAX_VALUE, domain, params) fold (
      error => error.failureNel,
      maxValue =>
        handleInt(CONF_MIN_VALUE, domain, params) fold (
          error => error.failureNel,
          minValue => {
            if (someNumber < minValue) {
              getMessage(locale, domain, MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_SHORT, messages).failureNel
            } else if (someNumber > maxValue) {
              getMessage(locale, domain, MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_LONG, messages).failureNel
            } else {
              someNumber.successNel
            }
          } //
        ) //
    )

}
