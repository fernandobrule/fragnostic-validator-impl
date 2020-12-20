package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorAdaptor
import org.slf4j.{ Logger, LoggerFactory }

import java.util.Locale

class CpfValidator extends ValidatorAdaptor[String] {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  override def doValidation(locale: Locale, cpf: String, args: Map[String, String]): Either[List[String], String] = {
    logger.warn("doValidation() - validaci\u00F3n de CPF sin implementar, se retorna tal cual")
    Right(cpf)
  }

}
