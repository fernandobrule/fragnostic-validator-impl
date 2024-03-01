package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class StringEmptyValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (text.trim.isEmpty) {
      getFailureNel(locale, domain, MSG_STRING_EMPTY_VALIDATOR_STRING_IS_EMPTY, messages)
    } else {
      text.trim.successNel
    }

}
