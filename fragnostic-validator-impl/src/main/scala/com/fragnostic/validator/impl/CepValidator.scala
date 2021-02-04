package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorApi
import scalaz.Scalaz._

class CepValidator extends ValidatorApi[String] {

  override def validate(cep: String, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (cep.trim.isEmpty) {
      messages(0).failureNel
    } else {
      cep.trim.successNel
    }

}
