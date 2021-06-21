package com.fragnostic.validator.support

import scala.util.Try

trait TypeShortHandler {

  def handleShort(param: String, domain: String, params: Map[String, String]): Either[String, Short] = {
    if (params.isEmpty) {
      Left(s"type.short.handler.error.params.are.empty.we.are.waiting.for\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else if (!params.contains(param)) {
      Left(s"type.short.handler.error.no.param\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toShort)) getOrElse Left(s"type.short.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }
  }

}
