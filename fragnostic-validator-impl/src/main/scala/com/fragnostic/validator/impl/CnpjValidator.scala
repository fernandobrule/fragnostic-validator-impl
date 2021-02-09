package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class CnpjValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(cnpj: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (!argsAreValid(numberExpected = 1, messages: _*)) {
      "cnpj.validator.wrong.number.of.messages".failureNel
    } else if (cnpj.trim.isEmpty) {
      messages(0).failureNel // i18n.getString(locale, "cnpj.validator.cnpj.empty").failureNel
    } else {
      cnpj.trim.successNel
    }

}
