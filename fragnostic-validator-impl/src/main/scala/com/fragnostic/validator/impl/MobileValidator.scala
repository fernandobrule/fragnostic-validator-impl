package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import scalaz.Scalaz._

trait MobileValidator extends UnderValidation {

  private val brazilDigitsRegex = """^\d{11}$"""

  private def validateBrazilMobile(mobile: String, errorMessage: String): StringValidation[String] = {
    val mobileFiltered = mobile.trim.filter(c => c.isDigit)
    if (mobileFiltered.matches(brazilDigitsRegex)) {
      mobileFiltered.successNel
    } else {
      errorMessage.failureNel
    }
  }

  def validateMobile(mobile: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (mobile.trim.isEmpty) {
      emptyTextMessage.failureNel
    } else {
      validateBrazilMobile(mobile, errorMessage)
    }

}
