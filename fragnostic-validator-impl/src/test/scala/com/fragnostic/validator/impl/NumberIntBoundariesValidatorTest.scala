package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberIntBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberIntBoundariesValidator = new NumberIntBoundariesValidator

  val domain = "Number Boundaries"

  val minValue: Int = 68
  val maxValue: Int = 153

  val params: Map[String, String] = Map("minValue" -> minValue.toString, "maxValue" -> maxValue.toString)

  describe("***Number Int Boundaries Validator Test***") {

    it("Can Validate Number") {

      val number: Int = 100
      val msgTooShort = validatorI18n.getFormattedString(locale, "number.int.boundaries.validator.number.is.too.short", List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        "number.int.boundaries.validator.number.is.too.short" -> msgTooShort)

      val validatedNumber: Int = numberIntBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => throw new IllegalStateException(error.head),
        number => number //
      )

      validatedNumber should be(number)
    }

    it("Can Validate Number Too Short") {

      val number: Int = 35
      val msgTooShort = validatorI18n.getFormattedString(locale, "number.int.boundaries.validator.number.is.too.short", List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        "number.int.boundaries.validator.number.is.too.short" -> msgTooShort)

      val nel = numberIntBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(msgTooShort)
    }

    it("Can Validate Number Too Long") {

      val number: Int = 450
      val msgTooLong = validatorI18n.getFormattedString(locale, "number.int.boundaries.validator.number.is.too.long", List(domain, number.toString, maxValue.toString))
      val messages: Map[String, String] = Map(
        "number.int.boundaries.validator.number.is.too.long" -> msgTooLong)

      val nel = numberIntBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(msgTooLong)
    }

  }

}