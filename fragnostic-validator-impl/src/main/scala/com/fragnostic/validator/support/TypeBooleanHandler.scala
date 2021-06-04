package com.fragnostic.validator.support

import scala.util.Try

trait TypeBooleanHandler {

  def handleBoolean(param: String, params: Map[String, String]): Either[String, Boolean] = {
    if (params.isEmpty) {
      Left(s"type.boolean.handler.error.params.are.empty")
    } else if (!params.contains(param)) {
      Left(s"type.boolean.handler.error.no.param_$param")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toBoolean)) getOrElse Left(s"type.boolean.handler.error.param.value.not.valid_$paramValue")
    }
  }

}
