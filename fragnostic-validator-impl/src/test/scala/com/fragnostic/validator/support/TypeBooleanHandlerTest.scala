package com.fragnostic.validator.support

import com.fragnostic.validator.impl.AgnosticLifeCycleValidatorTest

class TypeBooleanHandlerTest extends AgnosticLifeCycleValidatorTest with TypeBooleanHandler {

  describe("***Type Boolean Handler Test***") {

    val domain = "TypeBoolean"

    it("Can Handle Right Parameter") {
      val params: Map[String, String] = Map(CONF_HAS_TO_FORMAT -> "true")
      val message: String = handleBoolean(CONF_HAS_TO_FORMAT, domain, params) fold (
        error => error,
        valueBoolean => s"Value is $valueBoolean")

      assertResult(message)("Value is true")
    }

    it("Can Handle Wrong Parameter") {
      val paramValue = "true123"
      val params: Map[String, String] = Map(CONF_HAS_TO_FORMAT -> paramValue)
      val message: String = handleBoolean(CONF_HAS_TO_FORMAT, domain, params) fold (
        error => error,
        valueBoolean => s"Value is $valueBoolean")

      assertResult(message)(s"type.boolean.handler.error.param.value.not.valid_$paramValue.domain.is\u005b$domain\u005d")
    }

  }

}
