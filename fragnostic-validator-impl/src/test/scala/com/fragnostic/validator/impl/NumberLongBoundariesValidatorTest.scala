package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberLongBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberLongBoundariesValidator = new NumberLongBoundariesValidator

  val domain = "Number Boundaries"

  val minValue: Long = 6.8.toLong
  val maxValue: Long = 15.3.toLong

  val messages: Map[String, String] = Map(
    NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT -> validatorI18n.getString(locale, NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT),
    NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG -> validatorI18n.getString(locale, NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG) //
  )

  val params: Map[String, String] = Map("minValue" -> minValue.toString, "maxValue" -> maxValue.toString)

  describe("***Number Long Boundaries Validator Test***") {

    it("Can Validate Number Too Short") {

      val number = 3.5.toLong

      val nel = numberLongBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT, List(domain, number.toString, minValue.toString)))
    }

    it("Can Validate Number Too Long") {

      val number = 45.0.toLong

      val nel = numberLongBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG, List(domain, number.toString, maxValue.toString)))
    }

    it("Can Validate Message for Too Short does not exists") {

      val number = 5.0.toLong
      val messages: Map[String, String] = Map(
        NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG -> validatorI18n.getString(locale, NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG) //
      )

      val nel = numberLongBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(s"message___${NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT}___is.not.available")
    }

    it("Can Validate Message for Too Long does not exists") {

      val number = 45.0.toLong
      val messages: Map[String, String] = Map(
        NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT -> validatorI18n.getString(locale, NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_SHORT) //
      )

      val nel = numberLongBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(s"message___${NUMBER_LONG_BOUNDARIES_VALIDATOR_NUMBER_LONG_IS_TOO_LONG}___is.not.available")
    }

  }

}