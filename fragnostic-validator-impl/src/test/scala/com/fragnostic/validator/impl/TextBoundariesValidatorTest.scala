package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class TextBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val textBoundariesValidator = new TextBoundariesValidator
  val domain = "CPF"
  val minLength = "6"
  val maxLength = "15"

  val params: Map[String, String] = Map(
    CONF_MIN_LENGTH -> minLength,
    CONF_MAX_LENGTH -> maxLength //
  )

  describe("***Text Boundaries Validator Test***") {

    it("Can Validate Empty Text") {

      val text = ""
      val messages: Map[String, String] = Map(
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG) //
      )

      val nel = textBoundariesValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY))

    }

    it("Can Validate Null Text") {

      val text = null
      val messages: Map[String, String] = Map(
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_NULL -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_NULL),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG) //
      )

      val nel = textBoundariesValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_NULL))
    }

    it("Can Validate Not Mandatory Empty Text") {

      val text = ""
      val mandatory = false
      val messages: Map[String, String] = Map(
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG) //
      )

      val validation = textBoundariesValidator.validate(localePtBr, domain, text, params, messages, mandatory)
      assertResult(validation.isSuccess)(true)
    }

    it("Can Validate Text Too Short") {

      val text = "abc"
      val msgTooShort = validatorI18n.getFormattedString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT, List(domain, text.length.toString, minLength))
      val messages: Map[String, String] = Map(
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT -> msgTooShort,
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG) //
      )

      val nel = textBoundariesValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooShort)
    }

    it("Can Validate Text Too Long") {

      val text = "abcdedfjhskdlfjklsdjfkljsdklfjksldjfklsdjflkjfjsdf"
      val msgTooLong = validatorI18n.getFormattedString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG, List(domain, text.length.toString, maxLength))
      val messages: Map[String, String] = Map(
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_SHORT),
        MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> msgTooLong //
      )

      val nel = textBoundariesValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      // nel should not be Nil
      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooLong)
    }

  }

}