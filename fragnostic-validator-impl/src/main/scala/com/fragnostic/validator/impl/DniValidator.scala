package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

//
// https://es.wikipedia.org/wiki/Documento_Nacional_de_Identidad_(Argentina)
class DniValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(dni: String, locale: Locale, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (!argsAreValid(numberExpected = 1, messages: _*)) {
      "dni.validator.wrong.number.of.messages".failureNel
    } else if (dni.trim.isEmpty) {
      messages(0).failureNel // i18n.getString(locale, "dni.validator.dni.empty").failureNel
    } else {
      dni.trim.successNel
    }

}
