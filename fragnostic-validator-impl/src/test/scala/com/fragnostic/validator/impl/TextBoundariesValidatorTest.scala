package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class TextBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val textBoundariesValidator = new TextBoundariesValidator
  val domain = "CPF"
  val minLength = "6"
  val maxLength = "15"
  val params: Map[String, String] = Map("minLength" -> minLength, "maxLength" -> maxLength)

  describe("***Text Boundaries Validator Test***") {

    it("Can Validate Empty Text") {

      val text = ""

      val nel = textBoundariesValidator.validate(locale, domain, text, params, Nil) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "text.boundaries.validator.text.is.empty", List(domain)))

    }

    it("Can Validate Not Mandatory Empty Text") {

      val text = ""
      val mandatory = false

      val validation = textBoundariesValidator.validate(locale, domain, text, params, Nil, mandatory)
      validation.isSuccess should be(true)
    }

    it("Can Validate Text Too Short") {

      val text = "abc"

      val nel = textBoundariesValidator.validate(locale, domain, text, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "text.boundaries.validator.text.is.too.short", List(domain, text.length.toString, minLength)))
    }

    it("Can Validate Text Too Long") {

      val text = "abcdedfjhskdlfjklsdjfkljsdklfjksldjfklsdjflkjfjsdf"

      val nel = textBoundariesValidator.validate(locale, domain, text, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "text.boundaries.validator.text.is.too.long", List(domain, text.length.toString, maxLength)))
    }

  }

}