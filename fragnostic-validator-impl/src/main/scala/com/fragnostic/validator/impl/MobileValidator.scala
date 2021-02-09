package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class MobileValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(rawMobile: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    if (!argsAreValid(numberExpected = 3, messages: _*)) {
      "mobile.validator.wrong.number.of.messages".failureNel
    } else if (rawMobile.trim.isEmpty) {
      messages(0).failureNel
    } else {
      val numbers: List[Int] = rawMobile.filter(c => c.isDigit).map(c => c.asDigit).toList
      if (numbers.isEmpty) {
        messages(0).failureNel
      } else {
        val mobile: String = numbers.mkString("")
        val code: String = mobile.substring(0, 2)
        if (countryCodes.contains(code)) {
          if (hasToFormat) {
            format(mobile).successNel
          } else {
            mobile.successNel
          }
        } else {
          messages(2).failureNel
        }
      }
    }

  //
  // TODO colocar en un archivo
  // 54 - Argentina
  // 55 - Brasil
  // 56 - Chile
  // 598 - Uruguay
  private val countryCodes: List[String] = List("54", "55", "56", "598")

  private def format(mobile: String): String = {
    // TODO implementar format
    mobile
  }

}
