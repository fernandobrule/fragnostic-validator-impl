package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class TextBoundariesValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler {

  private[this] val logger: Logger = LoggerFactory.getLogger("TextBoundariesValidator")

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: List[String], mandatory: Boolean): Validated[String] =
    handleInt("maxLength", domain, params) fold (
      error => {
        logger.error(s"validate() - $error")
        error.failureNel
      },
      maxLength => {
        val textLength = text.length
        if (textLength > maxLength) {
          getErrorMessage(locale, "text.boundaries.validator.text.is.lengthier", List(textLength.toString, maxLength.toString), validatorI18n, idxTextLengthier, messages).failureNel
        } else if (text.trim.isEmpty) {
          if (mandatory) {
            getErrorMessage(locale, "text.boundaries.validator.text.is.empty", Nil, validatorI18n, idxTextEmpty, messages).failureNel
          } else {
            "".successNel
          }
        } else {
          text.trim.successNel
        }
      })

}
