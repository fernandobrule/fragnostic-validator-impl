package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class TextMaxLengthValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler with ValidatorMessagesKeys {

  private[this] val logger: Logger = LoggerFactory.getLogger("TextMaxLengthValidator")

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    handleInt("maxLength", domain, params) fold (
      error => {
        logger.error(s"validate() - $error")
        error.failureNel
      },
      maxLength => {
        val textLength = text.length
        if (textLength > maxLength) {
          messages.getOrElse(TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG, s"message___${TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG}___is.not.available").failureNel
        } else {
          text.trim.successNel
        }
      } //
    )

}
