package com.fragnostic.validator.support

import com.fragnostic.validator.impl.AgnosticLifeCycleValidatorTest

class TypeBooleanHandlerTest extends AgnosticLifeCycleValidatorTest with TypeBooleanHandler {

  val param = "hasToFormat"

  describe("***Type Boolean Handler Test***") {

    val domain = "TypeBoolean"

    it("Can Handle Right Parameter") {
      val params: Map[String, String] = Map(param -> "true")
      val message: String = handleBoolean(param, domain, params) fold (
        error => error,
        valueBoolean => s"Value is $valueBoolean")

      assertResult(message)("Value is true")
    }

    it("Can Handle Wrong Parameter") {
      val paramValue = "true123"
      val params: Map[String, String] = Map(param -> paramValue)
      val message: String = handleBoolean(param, domain, params) fold (
        error => error,
        valueBoolean => s"Value is $valueBoolean")

      assertResult(message)(s"type.boolean.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }

  }

}
