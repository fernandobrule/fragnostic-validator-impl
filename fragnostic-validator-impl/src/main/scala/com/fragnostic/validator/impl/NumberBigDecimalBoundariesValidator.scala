package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeBigDecimalHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberBigDecimalBoundariesValidator extends ValidatorApi[BigDecimal] with ValidatorSupport with TypeBigDecimalHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, someNumber: BigDecimal, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[BigDecimal] =
    Option(someNumber) match {
      case None => getMessage(NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_NULL, messages).failureNel
      case Some(someNumber) =>
        handleBigDecimal("maxValue", domain, params) fold (
          error => error.failureNel,
          maxValue =>
            handleBigDecimal("minValue", domain, params) fold (
              error => error.failureNel,
              minValue => {
                if (someNumber < minValue) {
                  getMessage(NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT, messages).failureNel
                } else if (someNumber > maxValue) {
                  getMessage(NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, messages).failureNel
                } else {
                  someNumber.successNel
                }
              } //
            ) //
        ) //
    }

}
