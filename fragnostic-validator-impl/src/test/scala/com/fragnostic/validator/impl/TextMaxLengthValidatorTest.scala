package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.NonEmptyList

class TextMaxLengthValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***Text Max Length Validator Test***") {

    val validatorI18n = new ValidatorI18n()
    val domain = "TextMaxLength"

    it("Can Validate Text Max Length That Compliant") {

      val textMaxLengthValidator = new TextMaxLengthValidator
      val params: Map[String, String] = Map("maxLength" -> "5")
      val text = "abcde"
      val validation: Validated[String] = textMaxLengthValidator.validate(locale, domain, text, params)
      validation.isSuccess should be(true)
      validation.toList.head should be(text)
    }

    it("Can Validate Text Max Length That Does Not Compliant") {

      val textMaxLengthValidator = new TextMaxLengthValidator
      val maxLength = "5"
      val params: Map[String, String] = Map("maxLength" -> maxLength)
      val text = "abcdef"

      val nel = textMaxLengthValidator.validate(locale, domain, text, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "text.max.length.validator.text.is.too.long", List(text.length.toString, maxLength)))
    }

    it("Can Validate Text Max Length That Does Not Compliant With External Message") {

      val textMaxLengthValidator = new TextMaxLengthValidator
      val maxLength = "5"
      val params: Map[String, String] = Map("maxLength" -> maxLength)
      val text = "abcdef"

      val nel = textMaxLengthValidator.validate(locale, domain, text, params, List("wqeqw", "wqasdeqw", "def")) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be("def")
    }

  }

}