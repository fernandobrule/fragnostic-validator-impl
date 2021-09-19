package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api.{ VALIDATOR_TEXT_NOT_VALID, Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

//
// Ref:
// http://www.macoratti.net/alg_cpf.htm
//
class CpfValidator extends ValidatorApi[String] with ValidatorSupport {

  private def textBoundariesValidator = new TextBoundariesValidator

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, cpf: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    textBoundariesValidator.validate(locale, i18n, domain, cpf, params, messages, mandatory) fold (
      error => error.head.failureNel,
      cpf =>
        if (cpf.trim.isEmpty && !mandatory) {
          cpf.successNel
        } else if (!isValid(cpf.trim)) {
          //messages(1).failureNel
          getErrorMessage(locale, "cpf.validator.cpf.is.not.valid", Nil, validatorI18n, VALIDATOR_TEXT_NOT_VALID, messages).failureNel
        } else {
          cpf.successNel
        })

  private val STRICT_STRIP_REGEX: String = """[.-]"""

  // TODO esta lista tiene que estar en un archivo
  val BLACKLIST: Array[String] = Array(
    "00000000000",
    "11111111111",
    "22222222222",
    "33333333333",
    "44444444444",
    "55555555555",
    "66666666666",
    "77777777777",
    "88888888888",
    "99999999999",
    "12345678909")

  private def addDigit(numbers: String): String =
    s"$numbers${verifierDigit(numbers)}"

  private def verifierDigit(digits: String): Int = {
    val numbers: List[Int] = digits.map(c => c.asDigit).toList
    val multiplied: List[Int] = numbers.zipWithIndex.map(ni => {
      ni._1 * ((numbers.size + 1) - ni._2)
    })

    val mod: Int = multiplied.sum % 11
    if (mod < 2) 0 else {
      11 - mod
    }
  }

  private def strip(number: String): String =
    number.replaceAll(STRICT_STRIP_REGEX, "")

  private def isValid(number: String): Boolean = {
    val stripped: String = strip(number)
    if (stripped.length != 11 || BLACKLIST.contains(stripped)) {
      false
    } else {
      stripped == addDigit(addDigit(stripped.substring(0, 9)))
    }
  }

}
