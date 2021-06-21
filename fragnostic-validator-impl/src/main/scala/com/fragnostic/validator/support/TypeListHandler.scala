package com.fragnostic.validator.support

import scala.util.Try

trait TypeListHandler {

  private def toList(paramValue: String): List[String] =
    paramValue.split(";").toList

  def handleList(param: String, domain: String, params: Map[String, String]): Either[String, List[String]] = {
    if (params.isEmpty) {
      Left(s"type.list.handler.error.params.are.empty.we.are.waiting.for\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else if (!params.contains(param)) {
      Left(s"type.list.handler.error.no.param\u005b$param\u005d.domain.is\u005b$domain\u005d")
    } else {
      val paramValue = params(param)
      Try(Right(toList(paramValue))) getOrElse Left(s"type.list.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }
  }

}
