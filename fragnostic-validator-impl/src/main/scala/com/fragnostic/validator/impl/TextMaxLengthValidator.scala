package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorI18n
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

class TextMaxLengthValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler {

  private[this] val logger: Logger = LoggerFactory.getLogger("TextMaxLengthValidator")

  override def validate(text: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] =
    if (text.trim.isEmpty) {
      getErrorMessage(locale, "text.max.length.validator.text.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {

      handleInt("maxLength", params) fold (
        error => {
          logger.error(s"validate() - $error")
          error.failureNel
        },
        maxLength => {
          val textLength = text.length
          if (textLength > maxLength) {
            getErrorMessage(locale, "text.max.length.validator.text.is.lengthier", List(textLength.toString, maxLength.toString), validatorI18n, 1, messages).failureNel
          } else {
            text.trim.successNel
          }
        })

    }

}
