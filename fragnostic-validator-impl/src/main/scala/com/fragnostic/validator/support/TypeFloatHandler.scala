package com.fragnostic.validator.support

import scala.util.Try

trait TypeFloatHandler {

  def handleFloat(param: String, domain: String, params: Map[String, String]): Either[String, Float] =
    if (params.isEmpty) {
      Left(s"type.float.handler.error.params.are.empty.we.are.waiting.for\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else if (!params.contains(param)) {
      Left(s"type.float.handler.error.no.param\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toFloat)) getOrElse Left(s"type.float.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }

}
