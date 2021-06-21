package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.NonEmptyList

class TextBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***Text Boundaries Validator Test***") {

    val validatorI18n = new ValidatorI18n()
    val textBoundariesValidator = new TextBoundariesValidator
    val domain = "TextBoundaries"

    it("Can Validate Empty Text") {

      val params: Map[String, String] = Map("maxLength" -> "5")
      val text = ""
      val nel = textBoundariesValidator.validate(locale, domain, text, params, Nil) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getString(locale, "text.boundaries.validator.text.is.empty"))

    }

    it("Can Validate Not Mandatory Empty Text") {

      val params: Map[String, String] = Map("maxLength" -> "5")
      val text = ""
      val mandatory = false

      val validation = textBoundariesValidator.validate(locale, domain, text, params, Nil, mandatory)
      validation.isSuccess should be(true)
    }

    it("Can Validate Text Max Length That Is Compliant") {

      val params: Map[String, String] = Map("maxLength" -> "5")
      val text = "abcde"
      val validation: Validated[String] = textBoundariesValidator.validate(locale, domain, text, params)
      validation.isSuccess should be(true)
      validation.toList.head should be(text)
    }

    it("Can Validate Text Max Length That Is Not Compliant") {

      val maxLength = "5"
      val params: Map[String, String] = Map("maxLength" -> maxLength)
      val text = "abcdef"

      val nel = textBoundariesValidator.validate(locale, domain, text, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "text.boundaries.validator.text.is.lengthier", List(text.length.toString, maxLength)))
    }

    it("Can Validate Text Max Length That Is Not Compliant With External Message") {

      val maxLength = "5"
      val params: Map[String, String] = Map("maxLength" -> maxLength)
      val text = "abcdef"

      val nel = textBoundariesValidator.validate(locale, domain, text, params, List("wqeqw", "wertqeqw", "def")) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be("def")
    }

  }

}