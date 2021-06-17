package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class TextEmptyValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: List[String], mandatory: Boolean = true): Validated[String] =
    if (text.trim.isEmpty) {
      getErrorMessage(locale, "text.empty.validator.text.is.empty", Nil, validatorI18n, idxTextEmpty, messages).failureNel
    } else {
      text.trim.successNel
    }

}
