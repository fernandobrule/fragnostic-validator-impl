package com.fragnostic.validator.support

import scala.util.Try

trait TypeBigDecimalHandler {

  def handleBigDecimal(param: String, domain: String, params: Map[String, String]): Either[String, BigDecimal] =
    if (params.isEmpty) {
      Left(s"type.big.decimal.handler.error.params.are.empty.we.are.waiting.for\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else if (!params.contains(param)) {
      Left(s"type.big.decimal.handler.error.no.param\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else {
      val paramValue = params(param)
      Try(Right(BigDecimal.apply(paramValue))) getOrElse Left(s"type.big.decimal.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }

}
