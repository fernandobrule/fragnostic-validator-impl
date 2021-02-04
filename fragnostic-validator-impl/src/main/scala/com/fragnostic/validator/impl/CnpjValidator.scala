package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorApi
import scalaz.Scalaz._

class CnpjValidator extends ValidatorApi[String] {

  override def validate(cnpj: String, hasToFormat: Boolean, messages: String*): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (cnpj.trim.isEmpty) {
      messages(0).failureNel
    } else {
      cnpj.trim.successNel
    }

}
