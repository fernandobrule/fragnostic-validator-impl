package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeBigDecimalHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class NumberBigDecimalValidator extends ValidatorApi[BigDecimal] with ValidatorSupport with TypeBigDecimalHandler with ValidatorMessagesKeys {

  //private[this] val logger: Logger = LoggerFactory.getLogger("NumberBigDecimalValidator")

  override def validate(locale: Locale, domain: String, someNumber: BigDecimal, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[BigDecimal] = {
    //logger.info(s"validate() - locale[$locale], domain[$domain], someNumber[$someNumber]")
    Option(someNumber) match {
      case None => getMessage(locale, domain, MSG_NUMBER_BIG_DECIMAL_VALIDATOR_NUMBER_IS_NULL, messages).failureNel
      case Some(someNumber) =>
        handleBigDecimal(CONF_MAX_VALUE, domain, params) fold (
          error => error.failureNel,
          maxValue =>
            handleBigDecimal(CONF_MIN_VALUE, domain, params) fold (
              error => error.failureNel,
              minValue => {
                if (someNumber < minValue) {
                  getMessage(locale, domain, MSG_NUMBER_BIG_DECIMAL_VALIDATOR_NUMBER_IS_TOO_SHORT, messages).failureNel
                } else if (someNumber > maxValue) {
                  getMessage(locale, domain, MSG_NUMBER_BIG_DECIMAL_VALIDATOR_NUMBER_IS_TOO_LONG, messages).failureNel
                } else {
                  someNumber.successNel
                }
              } //
            ) //
        ) //
    }
  }

}
