package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class TextEmptyValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (text.trim.isEmpty) {
      messages.getOrElse(TEXT_EMPTY_VALIDATOR_TEXT_IS_EMPTY, s"message___${TEXT_EMPTY_VALIDATOR_TEXT_IS_EMPTY}___is.not.available").failureNel
    } else {
      text.trim.successNel
    }

}
