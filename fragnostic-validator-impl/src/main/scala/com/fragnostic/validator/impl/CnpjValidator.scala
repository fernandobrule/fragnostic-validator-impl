package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class CnpjValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(cnpj: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] =
    // TODO esta es una implementación absolutamente mínima
    if (cnpj.trim.isEmpty) {
      getErrorMessage(locale, "cnpj.validator.cnpj.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {
      cnpj.trim.successNel
    }

}
