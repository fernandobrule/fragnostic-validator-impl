package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class CepValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(cep: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (argsAreValid(numberExpected = 1, messages: _*)) {
      "cep.validator.wrong.number.of.messages".failureNel
    } else if (cep.trim.isEmpty) {
      messages(0).failureNel // i18n.getString(locale, "cep.validator.cep.empty").failureNel
    } else {
      cep.trim.successNel
    }

}
