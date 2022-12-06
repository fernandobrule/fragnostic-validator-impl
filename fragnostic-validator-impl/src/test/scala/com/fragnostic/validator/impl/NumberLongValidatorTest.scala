package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberLongValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberLongValidator = new NumberLongValidator

  val domain = "NumberLong"

  val minValue: Long = 6.8.toLong
  val maxValue: Long = 15.3.toLong

  val messages: Map[String, String] = Map(
    MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_SHORT),
    MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_LONG) //
  )

  val params: Map[String, String] = Map(
    CONF_MIN_VALUE -> minValue.toString,
    CONF_MAX_VALUE -> maxValue.toString //
  )

  describe("***NumberLongValidatorTest***") {

    it("Can Validate Number Too Short") {

      val number = 3.5.toLong

      val nel = numberLongValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getFormattedString(localePtBr, MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_SHORT, List(domain, number.toString, minValue.toString)))
    }

    it("Can Validate Number Too Long") {

      val number = 45.0.toLong

      val nel = numberLongValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getFormattedString(localePtBr, MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_LONG, List(domain, number.toString, maxValue.toString)))
    }

    it("Can Validate Message for Too Short does not exists") {

      val number = 5.0.toLong
      val messages: Map[String, String] = Map(
        MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_LONG -> validatorI18n.getString(localePtBr, MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_LONG) //
      )

      val nel = numberLongValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(s"message___${localePtBr}___${MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_SHORT}___${domain}___is.not.available")
    }

    it("Can Validate Message for Too Long does not exists") {

      val number = 45.0.toLong
      val messages: Map[String, String] = Map(
        MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_SHORT) //
      )

      val nel = numberLongValidator.validate(localePtBr, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(s"message___${localePtBr}___${MSG_NUMBER_LONG_VALIDATOR_NUMBER_IS_TOO_LONG}___${domain}___is.not.available")
    }

  }

}