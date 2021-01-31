package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import scalaz.Scalaz._

trait MobileValidator extends UnderValidation {

  //
  // 54 - Argentina
  // 55 - Brasil
  // 56 - Chile
  // 598 - Uruguay
  private val countryCodes: List[String] = List("54", "55", "56", "598")

  def validateMobile(rawMobile: String, emptyTextMessage: String, withoutCountryCodeErrorMessage: String, errorMessage: String): StringValidation[String] =
    if (rawMobile.trim.isEmpty) {
      emptyTextMessage.failureNel
    } else {
      val numbers: List[Int] = rawMobile.filter(c => c.isDigit).map(c => c.asDigit).toList
      if (numbers.isEmpty) {
        emptyTextMessage.failureNel
      } else {
        val mobile: String = numbers.mkString("")
        val code: String = mobile.substring(0, 2)
        println(s">>> rawMobile[$rawMobile] mobile[$mobile], code[$code]")
        if (countryCodes.contains(code)) {
          mobile.successNel
        } else {
          withoutCountryCodeErrorMessage.failureNel
        }
      }
    }

}
