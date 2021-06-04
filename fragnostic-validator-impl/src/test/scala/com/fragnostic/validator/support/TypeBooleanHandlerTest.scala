package com.fragnostic.validator.support

import com.fragnostic.validator.impl.AgnosticLifeCycleValidatorTest

class TypeBooleanHandlerTest extends AgnosticLifeCycleValidatorTest with TypeBooleanHandler {

  val param = "hasToFormat"

  describe("***Type Boolean Handler Test***") {

    it("Can Handle Right Parameter") {
      val params: Map[String, String] = Map(param -> "true")
      val message: String = handleBoolean(param, params) fold (
        error => error,
        valueBoolean => s"Value is $valueBoolean")

      message should be("Value is true")
    }

    it("Can Handle Wrong Parameter") {
      val paramValue = "true123"
      val params: Map[String, String] = Map(param -> paramValue)
      val message: String = handleBoolean(param, params) fold (
        error => error,
        valueBoolean => s"Value is $valueBoolean")

      message should be(s"type.boolean.handler.error.param.value.not.valid_$paramValue")
    }

  }

}
