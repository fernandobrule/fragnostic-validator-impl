package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import scalaz.Scalaz._

trait EmptyTextValidator extends UnderValidation {

  def validateEmptyText(text: String, emptyTextMessage: String): StringValidation[String] =
    if (text.isEmpty) {
      emptyTextMessage.failureNel
    } else {
      text.trim.successNel
    }

}
