package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeBooleanHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale

class CepValidator extends ValidatorApi[String] with ValidatorSupport with TypeBooleanHandler {

  private val cepPattern = """(\d{5})(\d{3})""".r

  override def validate(cep: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] =
    if (cep.trim.isEmpty) {
      getErrorMessage(locale, "cep.validator.cep.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {
      handleBoolean("hasToFormat", params) fold (
        error => error.failureNel,
        hasToFormat => cep.filter(c => c.isDigit) match {
          case cepPattern(l, r) => if (hasToFormat) s"$l-$r".successNel else s"$l$r".successNel
          case _ => getErrorMessage(locale, "cep.validator.cep.not.valid", Nil, validatorI18n, 1, messages).failureNel
        })
    }

}
