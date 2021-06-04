package com.fragnostic.validator.support

import scala.util.Try

trait TypeShortHandler {

  def handleShort(param: String, params: Map[String, String]): Either[String, Short] = {
    if (params.isEmpty) {
      Left(s"type.short.handler.error.params.are.empty")
    } else if (!params.contains(param)) {
      Left(s"type.short.handler.error.no.param_$param")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toShort)) getOrElse Left(s"type.short.handler.error.param.value.not.valid_$paramValue")
    }
  }

}
