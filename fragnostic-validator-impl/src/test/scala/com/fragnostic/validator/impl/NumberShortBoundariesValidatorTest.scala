package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberShortBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberShortBoundariesValidator = new NumberShortBoundariesValidator

  val domain = "Number Boundaries"

  val minValue: Short = 6.8.toShort
  val maxValue: Short = 15.3.toShort

  val messages: Map[String, String] = Map(
    NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT -> validatorI18n.getString(locale, NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT),
    NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG -> validatorI18n.getString(locale, NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG) //
  )

  val params: Map[String, String] = Map("minValue" -> minValue.toString, "maxValue" -> maxValue.toString)

  describe("***Number Short Boundaries Validator Test***") {

    it("Can Validate Number Too Short") {

      val number = 3.5.toShort

      val nel = numberShortBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getFormattedString(locale, NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT, List(domain, number.toString, minValue.toString)))
    }

    it("Can Validate Number Too Long") {

      val number = 45.0.toShort

      val nel = numberShortBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(nel.size)(1)
      assertResult(nel.head)(validatorI18n.getFormattedString(locale, NUMBER_SHORT_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, List(domain, number.toString, maxValue.toString)))
    }

  }

}