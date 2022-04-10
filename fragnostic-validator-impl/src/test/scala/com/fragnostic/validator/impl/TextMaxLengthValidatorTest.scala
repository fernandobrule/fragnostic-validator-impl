package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.NonEmptyList

class TextMaxLengthValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***Text Max Length Validator Test***") {

    val validatorI18n = new ValidatorI18n()
    val domain = "TextMaxLength"
    val messages: Map[String, String] = Map(
      TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG -> validatorI18n.getString(locale, TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG))

    it("Can Validate Text Max Length That Compliant") {

      val textMaxLengthValidator = new TextMaxLengthValidator
      val params: Map[String, String] = Map("maxLength" -> "5")
      val text = "abcde"
      val validation: Validated[String] = textMaxLengthValidator.validate(locale, domain, text, params, messages)
      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(text)
    }

    it("Can Validate Text Max Length That Does Not Compliant") {

      val textMaxLengthValidator = new TextMaxLengthValidator
      val maxLength = "5"
      val params: Map[String, String] = Map("maxLength" -> maxLength)
      val text = "abcdef"
      val msgTooLong = validatorI18n.getFormattedString(locale, TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG, List(text.length.toString, maxLength))
      val messages: Map[String, String] = Map(
        TEXT_MAX_LENGTH_VALIDATOR_TEXT_IS_TOO_LONG -> msgTooLong //
      )

      val nel = textMaxLengthValidator.validate(locale, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooLong)
    }

  }

}