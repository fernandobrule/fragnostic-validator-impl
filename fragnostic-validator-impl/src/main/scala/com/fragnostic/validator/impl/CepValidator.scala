package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class CepValidator extends ValidatorApi[String] with ValidatorSupport {

  private val cepPattern = """(\d{5})(\d{3})""".r

  override def validate(cep: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    if (!argsAreValid(numberExpected = 2, messages: _*)) {
      "cep.validator.wrong.number.of.messages".failureNel
    } else if (cep.trim.isEmpty) {
      messages(0).failureNel
    } else {
      cep.filter(c => c.isDigit) match {
        case cepPattern(l, r) => if (hasToFormat) s"$l-$r".successNel else s"$l$r".successNel
        case _ => messages(1).failureNel
      }
    }

}
