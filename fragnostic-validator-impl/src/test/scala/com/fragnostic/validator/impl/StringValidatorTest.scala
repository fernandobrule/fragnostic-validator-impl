package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class StringValidatorTest extends AgnosticLifeCycleValidatorTest {

  val stringValidator = new StringValidator
  val domain = "CPF"
  val minLength = "6"
  val maxLength = "15"

  val params: Map[String, String] = Map(
    CONF_MIN_LENGTH -> minLength,
    CONF_MAX_LENGTH -> maxLength //
  )

  describe("***StringValidatorTest***") {

    it("Can Validate Empty String") {

      val text = ""
      val messages: Map[String, String] = Map(
        MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_EMPTY),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG) //
      )

      val nel = stringValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_EMPTY))

    }

    it("Can Validate Null String") {

      val text = null
      val messages: Map[String, String] = Map(
        MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_EMPTY),
        MSG_STRING_VALIDATOR_STRING_IS_NULL -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_NULL),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG) //
      )

      val nel = stringValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_NULL))
    }

    it("Can Validate Not Mandatory Empty String") {

      val text = ""
      val mandatory = false
      val messages: Map[String, String] = Map(
        MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_EMPTY),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG) //
      )

      val validation = stringValidator.validate(localePtBr, domain, text, params, messages, mandatory)
      assertResult(validation.isSuccess)(true)
    }

    it("Can Validate String Too Short") {

      val text = "abc"
      val msgTooShort = validatorI18n.getFormattedString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT, List(domain, text.length.toString, minLength))
      val messages: Map[String, String] = Map(
        MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_EMPTY),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> msgTooShort,
        MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG) //
      )

      val nel = stringValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooShort)
    }

    it("Can Validate String Too Long") {

      val text = "abcdedfjhskdlfjklsdjfkljsdklfjksldjfklsdjflkjfjsdf"
      val msgTooLong = validatorI18n.getFormattedString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG, List(domain, text.length.toString, maxLength))
      val messages: Map[String, String] = Map(
        MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_EMPTY),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT),
        MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> msgTooLong //
      )

      val nel = stringValidator.validate(localePtBr, domain, text, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      // nel should not be Nil
      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooLong)
    }

  }

}