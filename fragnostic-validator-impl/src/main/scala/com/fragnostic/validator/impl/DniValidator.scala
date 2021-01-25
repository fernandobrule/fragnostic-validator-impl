package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

//
// https://es.wikipedia.org/wiki/Documento_Nacional_de_Identidad_(Argentina)
trait DniValidator extends UnderValidation {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def validateDni(dni: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] = {
    // TODO esta es una implementación absolutamente mínima
    logger.warn("validateDni() - esta es una implementación absolutamente mínima")
    if (dni.trim.isEmpty) {
      errorMessage.failureNel
    } else {
      dni.trim.successNel
    }
  }

}
