package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class CnpjValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(locale: Locale, domain: String, cnpj: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (cnpj.trim.isEmpty) {
      messages("cnpj.validator.cnpj.is.empty").failureNel
    } else {
      cnpj.trim.successNel
    }

}
