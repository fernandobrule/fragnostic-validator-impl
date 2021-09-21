package com.fragnostic.validator.impl

class TextBoundariesNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***TextBoundariesNotMandatoryValidatorTest***") {

    val textBoundariesValidator = new TextBoundariesValidator
    val domain = "TextBoundaries"

    it("Can Validate Empty Text") {

      val params: Map[String, String] = Map("maxLength" -> "5")
      val text = ""
      val mandatory = false
      val validator = textBoundariesValidator.validate(locale, validatorI18n, domain, text, params, Map.empty, mandatory)

      validator.isSuccess should be(true)

    }

  }

}