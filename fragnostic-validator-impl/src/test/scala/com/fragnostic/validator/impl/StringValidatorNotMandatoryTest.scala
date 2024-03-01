package com.fragnostic.validator.impl

class StringValidatorNotMandatoryTest extends AgnosticLifeCycleValidatorTest {

  describe("***StringValidatorNotMandatoryTest***") {

    val stringValidator = new StringValidator
    val domain = "String"

    it("Can Validate Empty String") {

      val params: Map[String, String] = Map(CONF_MAX_LENGTH -> "5")
      val text = ""
      val mandatory = false
      val validator = stringValidator.validate(localePtBr, domain, text, params, Map.empty, mandatory)

      assertResult(validator.isSuccess)(true)

    }

  }

}