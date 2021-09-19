package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.{ TypeBooleanHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class CepValidator extends ValidatorApi[String] with ValidatorSupport with TypeBooleanHandler {

  private val cepPattern = """(\d{5})(\d{3})""".r

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, cep: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    if (cep.trim.isEmpty) {
      if (mandatory) {
        getErrorMessage(locale, "cep.validator.cep.is.empty", Nil, validatorI18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
      } else {
        "".successNel
      }
    } else {
      handleBoolean("hasToFormat", domain, params) fold (
        error => error.failureNel,
        hasToFormat => cep.filter(c => c.isDigit) match {
          case cepPattern(l, r) => if (hasToFormat) s"$l-$r".successNel else s"$l$r".successNel
          case _ => getErrorMessage(locale, "cep.validator.cep.not.valid", Nil, validatorI18n, VALIDATOR_TEXT_NOT_VALID, messages).failureNel
        })
    }
  }

}
