package com.fragnostic.validator.impl

class TextBoundariesNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***TextBoundariesNotMandatoryValidatorTest***") {

    val textBoundariesValidator = new TextBoundariesValidator
    val domain = "TextBoundaries"

    it("Can Validate Empty Text") {

      val params: Map[String, String] = Map(CONF_MAX_LENGTH -> "5")
      val text = ""
      val mandatory = false
      val validator = textBoundariesValidator.validate(localePtBr, domain, text, params, Map.empty, mandatory)

      assertResult(validator.isSuccess)(true)

    }

  }

}