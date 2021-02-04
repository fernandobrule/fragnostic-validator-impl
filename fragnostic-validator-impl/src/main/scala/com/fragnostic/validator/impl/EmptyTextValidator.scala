package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorApi
import scalaz.Scalaz._

trait EmptyTextValidator extends ValidatorApi[String] {

  override def validate(text: String, hasToFormat: Boolean, messages: String*): Validated[String] =
    if (text.trim.isEmpty) {
      messages(0).failureNel // emptyTextMessage
    } else {
      text.trim.successNel
    }

}
