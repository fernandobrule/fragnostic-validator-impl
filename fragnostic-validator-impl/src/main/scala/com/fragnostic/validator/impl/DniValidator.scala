package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

//
// https://es.wikipedia.org/wiki/Documento_Nacional_de_Identidad_(Argentina)
class DniValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  override def validate(locale: Locale, domain: String, dni: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    Option(dni) match {
      case None => getMessage(locale, domain, MSG_DNI_VALIDATOR_DNI_IS_NULL, messages).failureNel
      case Some(dni) =>
        if (dni.trim.isEmpty) {
          getFailureNel(locale, domain, MSG_DNI_VALIDATOR_DNI_IS_EMPTY, messages)
        } else {
          dni.trim.successNel
        }
    }

}
