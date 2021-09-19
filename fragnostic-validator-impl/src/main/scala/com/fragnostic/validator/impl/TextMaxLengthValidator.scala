package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api.{ VALIDATOR_TEXT_TOO_LONG, Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class TextMaxLengthValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler {

  private[this] val logger: Logger = LoggerFactory.getLogger("TextMaxLengthValidator")

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    handleInt("maxLength", domain, params) fold (
      error => {
        logger.error(s"validate() - $error")
        error.failureNel
      },
      maxLength => {
        val textLength = text.length
        if (textLength > maxLength) {
          getErrorMessage(locale, "text.max.length.validator.text.is.too.long", List(textLength.toString, maxLength.toString), validatorI18n, VALIDATOR_TEXT_TOO_LONG, messages).failureNel
        } else {
          text.trim.successNel
        }
      })

}
