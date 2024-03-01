package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.NonEmptyList

class StringMaxLengthValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***String Max Length Validator Test***") {

    val validatorI18n = new ValidatorI18n()
    val domain = "StringMaxLength"
    val messages: Map[String, String] = Map(
      MSG_STRING_MAX_LENGTH_VALIDATOR_STRING_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_STRING_MAX_LENGTH_VALIDATOR_STRING_IS_TOO_LONG))

    it("Can Validate String Max Length That Compliant") {

      val stringMaxLengthValidator = new StringMaxLengthValidator
      val params: Map[String, String] = Map(CONF_MAX_LENGTH -> "5")
      val text = "abcde"
      val validation: Validated[String] = stringMaxLengthValidator.validate(localePtBr, domain, text, params, messages)
      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(text)
    }

    it("Can Validate String Max Length That Does Not Compliant") {

      val stringMaxLengthValidator = new StringMaxLengthValidator
      val maxLength = "5"
      val params: Map[String, String] = Map(CONF_MAX_LENGTH -> maxLength)
      val text = "abcdef"
      val msgTooLong = validatorI18n.getFormattedString(localePtBr, MSG_STRING_MAX_LENGTH_VALIDATOR_STRING_IS_TOO_LONG, List(text.length.toString, maxLength))
      val messages: Map[String, String] = Map(
        MSG_STRING_MAX_LENGTH_VALIDATOR_STRING_IS_TOO_LONG -> msgTooLong //
      )

      val nel = stringMaxLengthValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooLong)
    }

  }

}