package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale
import scala.annotation.tailrec

class CnpjValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  private val cnpjValidatorParams: Map[String, String] = Map(
    CONF_MIN_LENGTH -> "14",
    CONF_MAX_LENGTH -> "18" //
  )

  override def validate(locale: Locale, domain: String, cnpj: String, params_ : Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    Option(cnpj) match {
      case None =>
        getFailureNel(locale, domain, MSG_CNPJ_VALIDATOR_CNPJ_IS_NULL, messages)
      case Some(cnpj) =>
        textValidator.validate(locale, domain, cnpj, cnpjValidatorParams, textValidatorMessages(locale, domain, messages), mandatory) fold (
          error => error.head.failureNel,
          cnpj => {
            if (cnpj.trim.isEmpty && !mandatory) {
              cnpj.successNel
            } else if (isValid(cnpj.trim)) {
              cnpj.successNel
            } else {
              getFailureNel(locale, domain, MSG_CNPJ_VALIDATOR_CNPJ_IS_NOT_VALID, messages)
            }
          } //
        ) //
    }
  }

  private def textValidator = new TextValidator

  val BLACKLIST: Array[String] = Array(
    "00000000000000",
    "11111111111111",
    "22222222222222",
    "33333333333333",
    "44444444444444",
    "55555555555555",
    "66666666666666",
    "77777777777777",
    "88888888888888",
    "99999999999999" //
  )

  private def textValidatorMessages(locale: Locale, domain: String, messages: Map[String, String]): Map[String, String] = Map(
    MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY -> getMessage(locale, domain, MSG_CNPJ_VALIDATOR_CNPJ_IS_EMPTY, messages),
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT -> getMessage(locale, domain, MSG_CNPJ_VALIDATOR_CNPJ_IS_TOO_SHORT, messages),
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG -> getMessage(locale, domain, MSG_CNPJ_VALIDATOR_CNPJ_IS_TOO_LONG, messages) //
  )

  private val STRICT_STRIP_REGEX: String = """[.\-/]"""

  private def strip(number: String): String = {
    number.replaceAll(STRICT_STRIP_REGEX, "")
  }

  @tailrec
  private def suma(index: Int, cnpj: String, sum: Int, weight: Int): Int = {
    val nextSum = sum + ((cnpj.charAt(index) - 48) * weight)
    if (index > 0) {
      val nextPeso = {
        if ((weight + 1) == 10) {
          2
        } else {
          weight + 1
        }
      }

      suma(index - 1, cnpj, nextSum, nextPeso)
    } else {
      nextSum
    }
  }

  private def getFirstsVerifierDigit(cnpj: String): Char = {
    getVerifierDigit(cnpj)(11)
  }

  private def getSecondVerifierDigit(cnpj: String): Char = {
    getVerifierDigit(cnpj)(12)
  }

  private def getVerifierDigit(cnpj: String)(index: Int): Char = {
    val remainder = suma(index, cnpj, 0, 2) % 11
    if (remainder == 0 || remainder == 1) {
      '0'
    } else {
      ((11 - remainder) + 48).toChar
    }
  }

  private def isValid(rawCnpj: String): Boolean = {
    val strippedCnpj: String = strip(rawCnpj)
    if (strippedCnpj.length != 14 || BLACKLIST.contains(strippedCnpj)) {
      false
    } else {
      (getFirstsVerifierDigit(strippedCnpj) == strippedCnpj.charAt(12)) && (getSecondVerifierDigit(strippedCnpj) == strippedCnpj.charAt(13))
    }
  }

}
