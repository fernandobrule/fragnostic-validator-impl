package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class TextEmptyValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (text.trim.isEmpty) {
      getErrorMessage(locale, "text.empty.validator.text.is.empty", Nil, i18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
    } else {
      text.trim.successNel
    }

}
