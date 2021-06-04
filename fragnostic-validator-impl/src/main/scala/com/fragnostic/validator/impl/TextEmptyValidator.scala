package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class TextEmptyValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(text: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] =
    if (text.trim.isEmpty) {
      getErrorMessage(locale, "text.empty.validator.text.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {
      text.trim.successNel
    }

}
