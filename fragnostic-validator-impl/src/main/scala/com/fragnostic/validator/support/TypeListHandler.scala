package com.fragnostic.validator.support

import scala.util.Try

trait TypeListHandler {

  private def toList(paramValue: String): List[String] =
    paramValue.split(";").toList

  def handleList(param: String, params: Map[String, String]): Either[String, List[String]] = {
    if (params.isEmpty) {
      Left(s"type.list.handler.error.params.are.empty")
    } else if (!params.contains(param)) {
      Left(s"type.list.handler.error.no.param_$param")
    } else {
      val paramValue = params(param)
      Try(Right(toList(paramValue))) getOrElse Left(s"type.list.handler.error.param.value.not.valid_$paramValue")
    }
  }

}
