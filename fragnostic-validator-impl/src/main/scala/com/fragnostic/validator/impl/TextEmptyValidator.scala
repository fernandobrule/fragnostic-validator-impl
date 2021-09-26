package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class TextEmptyValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(locale: Locale, domain: String, text: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (text.trim.isEmpty) {
      messages("text.empty.validator.text.is.empty").failureNel
    } else {
      text.trim.successNel
    }

}
