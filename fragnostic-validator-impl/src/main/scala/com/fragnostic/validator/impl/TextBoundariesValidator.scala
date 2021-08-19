package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class TextBoundariesValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler {

  private[this] val logger: Logger = LoggerFactory.getLogger("TextBoundariesValidator")

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: List[String], mandatory: Boolean): Validated[String] =
    if (text.trim.isEmpty) {
      if (mandatory) {
        getErrorMessage(locale, "text.boundaries.validator.text.is.empty", List(domain), validatorI18n, idxTextEmpty, messages).failureNel
      } else {
        "".successNel
      }
    } else {
      handleInt("maxLength", domain, params) fold (
        error => error.failureNel,
        maxLength =>
          handleInt("minLength", domain, params) fold (
            error => error.failureNel,
            minLength => {
              val textLength = text.length
              if (textLength < minLength) {
                getErrorMessage(locale, "text.boundaries.validator.text.is.too.short", List(domain, textLength.toString, minLength.toString), validatorI18n, idxTextTooShort, messages).failureNel
              } else if (textLength > maxLength) {
                getErrorMessage(locale, "text.boundaries.validator.text.is.too.long", List(domain, textLength.toString, maxLength.toString), validatorI18n, idxTextTooLong, messages).failureNel
              } else {
                text.trim.successNel
              }
            } //
          ) //
      )
    }

}
