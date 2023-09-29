package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

//
// Ref:
// http://www.macoratti.net/alg_cpf.htm
//
class CpfValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  private def stringValidator = new StringValidator

  // 092.419.011-60
  // 09241901160
  private val cpfValidatorParams: Map[String, String] = Map(
    CONF_MIN_LENGTH -> "11",
    CONF_MAX_LENGTH -> "14" //
  )

  private def stringValidatorMessages(locale: Locale, domain: String, messages: Map[String, String]): Map[String, String] = Map(
    MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> getMessage(locale, domain, MSG_CPF_VALIDATOR_CPF_IS_EMPTY, messages),
    MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> getMessage(locale, domain, MSG_CPF_VALIDATOR_CPF_IS_TOO_SHORT, messages),
    MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> getMessage(locale, domain, MSG_CPF_VALIDATOR_CPF_IS_TOO_LONG, messages) //
  )

  override def validate(locale: Locale, domain: String, cpf: String, params_ : Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    Option(cpf) match {
      case None =>
        getFailureNel(locale, domain, MSG_CPF_VALIDATOR_CPF_IS_NULL, messages)
      case Some(cpf) =>
        stringValidator.validate(locale, domain, cpf, cpfValidatorParams, stringValidatorMessages(locale, domain, messages), mandatory) fold (
          error => error.head.failureNel,
          cpf =>
            if (cpf.trim.isEmpty && !mandatory) {
              cpf.successNel
            } else {
              val stripped: String = strip(cpf)
              if (isValid(stripped)) {
                stripped.successNel
              } else {
                getFailureNel(locale, domain, MSG_CPF_VALIDATOR_CPF_IS_NOT_VALID, messages)
              }
            } //
        ) //
    }
  }

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

  private def addDigit(numbers: String): String = {
    s"$numbers${verifierDigit(numbers)}"
  }

  private def verifierDigit(digits: String): Int = {
    val numbers: List[Int] = digits.map(c => c.asDigit).toList
    val multiplied: List[Int] = numbers.zipWithIndex.map(ni => {
      ni._1 * ((numbers.size + 1) - ni._2)
    })

    val mod: Int = multiplied.sum % 11
    if (mod < 2) {
      0
    } else {
      11 - mod
    }
  }

  private val STRICT_STRIP_REGEX: String = """[.\-]"""

  private def strip(number: String): String = {
    number.trim.replaceAll(STRICT_STRIP_REGEX, "")
  }

  private def isValid(stripped: String): Boolean = {
    if (stripped.length != 11 || BLACKLIST.contains(stripped)) {
      false
    } else {
      stripped == addDigit(addDigit(stripped.substring(0, 9)))
    }
  }

}
