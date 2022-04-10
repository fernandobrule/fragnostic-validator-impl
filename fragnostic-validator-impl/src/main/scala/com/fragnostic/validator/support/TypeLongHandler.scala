package com.fragnostic.validator.support

import scala.util.Try

trait TypeLongHandler {

  def handleLong(param: String, domain: String, params: Map[String, String]): Either[String, Long] =
    if (params.isEmpty) {
      Left(s"type.long.handler.error.params.are.empty.we.are.waiting.for\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else if (!params.contains(param)) {
      Left(s"type.long.handler.error.no.param\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toLong)) getOrElse Left(s"type.long.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }

}
