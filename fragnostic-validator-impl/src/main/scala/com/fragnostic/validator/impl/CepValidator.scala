package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeBooleanHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class CepValidator extends ValidatorApi[String] with ValidatorSupport with TypeBooleanHandler with ValidatorMessagesKeys {

  private val cepPattern = """(\d{5})(\d{3})""".r

  override def validate(locale: Locale, domain: String, cep: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    if (cep.trim.isEmpty) {
      if (mandatory) {
        getFailureNel(CEP_VALIDATOR_CEP_IS_EMPTY, messages)
      } else {
        "".successNel
      }
    } else {
      handleBoolean("hasToFormat", domain, params) fold (
        error => error.failureNel,
        hasToFormat => cep.filter(c => c.isDigit) match {
          case cepPattern(l, r) => if (hasToFormat) s"$l-$r".successNel else s"$l$r".successNel
          case _ => messages.getOrElse(CEP_VALIDATOR_CEP_IS_NOT_VALID, s"message___${CEP_VALIDATOR_CEP_IS_NOT_VALID}___is.not.vailable").failureNel
        })
    }
  }

}
