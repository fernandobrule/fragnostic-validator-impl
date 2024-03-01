package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberIntValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberIntValidator = new NumberIntValidator

  val domain = "NumberInt"

  val minValue: Int = 68
  val maxValue: Int = 153

  val params: Map[String, String] = Map(
    CONF_MIN_VALUE -> minValue.toString,
    CONF_MAX_VALUE -> maxValue.toString //
  )

  describe("***NumberIntValidatorTest***") {

    it("Can Validate Number") {

      val number: Int = 100
      val msgTooShort = validatorI18n.getFormattedString(localePtBr, MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_SHORT, List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_SHORT -> msgTooShort //
      )

      val validatedNumber: Int = numberIntValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => throw new IllegalStateException(error.head),
        number => number //
      )

      assertResult(validatedNumber)(number)
    }

    it("Can Validate Number Too Short") {

      val number: Int = 35
      val msgTooShort = validatorI18n.getFormattedString(localePtBr, MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_SHORT, List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_SHORT -> msgTooShort //
      )

      val nel = numberIntValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooShort)
    }

    it("Can Validate Number Too Long") {

      val number: Int = 450
      val msgTooLong = validatorI18n.getFormattedString(localePtBr, MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_LONG, List(domain, number.toString, maxValue.toString))
      val messages: Map[String, String] = Map(
        MSG_NUMBER_INT_VALIDATOR_NUMBER_IS_TOO_LONG -> msgTooLong //
      )

      val nel = numberIntValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      assertResult(nel.size)(1)
      assertResult(nel.head)(msgTooLong)
    }

  }

}