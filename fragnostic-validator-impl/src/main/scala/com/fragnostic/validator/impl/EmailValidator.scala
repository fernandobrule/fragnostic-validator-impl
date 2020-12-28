package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

trait EmailValidator extends UnderValidation {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def validateEmail(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] = {
    // TODO esta es una implementación absolutamente mínima
    logger.warn("validateEmail() - esta es una implementación absolutamente mínima")
    if (email.isEmpty) {
      emptyTextMessage.failureNel
    } else if (!email.contains("@")) {
      errorMessage.failureNel
    } else {
      email.trim.successNel
    }
  }

}
