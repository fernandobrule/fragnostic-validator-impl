package com.fragnostic.validator.impl

class TextValidatorNotMandatoryTest extends AgnosticLifeCycleValidatorTest {

  describe("***TextValidatorNotMandatoryTest***") {

    val textValidator = new TextValidator
    val domain = "Text"

    it("Can Validate Empty Text") {

      val params: Map[String, String] = Map(CONF_MAX_LENGTH -> "5")
      val text = ""
      val mandatory = false
      val validator = textValidator.validate(localePtBr, domain, text, params, Map.empty, mandatory)

      assertResult(validator.isSuccess)(true)

    }

  }

}