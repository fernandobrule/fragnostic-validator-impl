package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class TextBoundariesValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler with ValidatorMessagesKeys {

  private[this] val logger: Logger = LoggerFactory.getLogger("TextBoundariesValidator")

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean): Validated[String] = {
    //logger.info(s"validate() - enter")
    Option(text) match {
      case None => getFailureNel(locale, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_NULL, messages)
      case Some(text) =>
        if (text.trim.isEmpty) {
          if (mandatory) {
            getFailureNel(locale, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY, messages)
          } else {
            "".successNel
          }
        } else {
          handleInt(CONF_MAX_LENGTH, domain, params) fold (
            error => error.failureNel,
            maxLength =>
              handleInt(CONF_MIN_LENGTH, domain, params) fold (
                error => error.failureNel,
                minLength => {
                  val textTrim = text.trim
                  val textLength = textTrim.length
                  if (textLength < minLength) {
                    getFailureNel(locale, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT, messages)
                  } else if (textLength > maxLength) {
                    getFailureNel(locale, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG, messages)
                  } else {
                    textTrim.successNel
                  }
                } //
              ) //
          )
        }
    }
  }

}
