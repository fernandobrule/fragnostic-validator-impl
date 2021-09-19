package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

//
// https://es.wikipedia.org/wiki/Documento_Nacional_de_Identidad_(Argentina)
class DniValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(locale: Locale, i18n: ResourceI18n, domain: String, dni: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (dni.trim.isEmpty) {
      getErrorMessage(locale, "dni.validator.dni.is.empty", Nil, validatorI18n, VALIDATOR_TEXT_EMPTY, messages).failureNel
    } else {
      dni.trim.successNel
    }

}
