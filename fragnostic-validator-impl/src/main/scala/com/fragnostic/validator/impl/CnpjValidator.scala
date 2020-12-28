package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

trait CnpjValidator extends UnderValidation {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def validateCnpj(cnpj: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] = {
    // TODO esta es una implementación absolutamente mínima
    logger.warn("validateCnpj() - esta es una implementación absolutamente mínima")
    if (cnpj.isEmpty) {
      errorMessage.failureNel
    } else {
      cnpj.trim.successNel
    }
  }

}
