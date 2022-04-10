package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeIntHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class TextMaxLengthValidator extends ValidatorApi[String] with ValidatorSupport with TypeIntHandler with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    handleInt("maxLength", domain, params) fold (
      error => error.failureNel,
      maxLength => {
        val textLength = text.length
        if (textLength > maxLength) {
          getFailureNel(TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG, messages)
        } else {
          text.trim.successNel
        }
      } //
    )

}
