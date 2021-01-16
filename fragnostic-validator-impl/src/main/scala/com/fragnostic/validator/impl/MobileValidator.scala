package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

trait MobileValidator extends UnderValidation {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def validateMobile(mobile: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] = {
    // TODO esta es una implementación absolutamente mínima
    logger.warn("validateMobile() - esta es una implementación absolutamente mínima")
    if (mobile.trim.isEmpty) {
      emptyTextMessage.failureNel
    } else if (!mobile.trim.startsWith("55")) {
      errorMessage.failureNel
    } else {
      mobile.trim.successNel
    }
  }

}
