package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class EmptyTextValidator(emptyTextKey: String = "empty.text.validator.empty.text") extends ValidatorApi[String] with ValidatorSupport {

  override def validate(text: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    if (argsAreValid(numberExpected = 1, messages: _*)) {
      "empty.text.validator.wrong.number.of.messages".failureNel
    } else if (text.trim.isEmpty) {
      messages(0).failureNel
    } else {
      text.trim.successNel
    }

}
