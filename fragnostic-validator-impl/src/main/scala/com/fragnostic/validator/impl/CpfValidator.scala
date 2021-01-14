package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import scalaz.Scalaz._

//
// Ref:
// http://www.macoratti.net/alg_cpf.htm
//
trait CpfValidator extends UnderValidation {

  def validateCpf(cpf: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] = {
    if (cpf.isEmpty) {
      emptyTextMessage.failureNel
    } else if (!isValid(cpf)) {
      errorMessage.failureNel
    } else {
      cpf.trim.successNel
    }
  }

  private val STRICT_STRIP_REGEX: String = """[.-]"""
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
