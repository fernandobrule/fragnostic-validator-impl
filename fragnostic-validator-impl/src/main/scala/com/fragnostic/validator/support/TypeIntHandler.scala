package com.fragnostic.validator.support

import scala.util.Try

trait TypeIntHandler {

  def handleInt(param: String, params: Map[String, String]): Either[String, Int] = {
    if (params.isEmpty) {
      Left(s"type.int.handler.error.params.are.empty")
    } else if (!params.contains(param)) {
      Left(s"type.int.handler.error.no.param_$param")
    } else {
      val paramValue = params(param)
      Try(Right(paramValue.toInt)) getOrElse Left(s"type.int.handler.error.param.value.not.valid_$paramValue")
    }
  }

}
