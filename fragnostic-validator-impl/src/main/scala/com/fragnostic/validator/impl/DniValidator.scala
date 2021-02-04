package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorApi
import scalaz.Scalaz._

//
// https://es.wikipedia.org/wiki/Documento_Nacional_de_Identidad_(Argentina)
trait DniValidator extends ValidatorApi[String] {

  override def validate(dni: String, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (dni.trim.isEmpty) {
      messages(1).failureNel
    } else {
      dni.trim.successNel
    }

}
