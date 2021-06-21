package com.fragnostic.validator.support

import scala.util.Try

trait TypeBooleanHandler {

  def handleBoolean(param: String, domain: String, params: Map[String, String]): Either[String, Boolean] = {
    if (params.isEmpty) {
      Left(s"type.boolean.handler.error.params.are.empty.we.are.waiting.for\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else if (!params.contains(param)) {
      Left(s"type.boolean.handler.error.no.param\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toBoolean)) getOrElse Left(s"type.boolean.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }
  }

}
