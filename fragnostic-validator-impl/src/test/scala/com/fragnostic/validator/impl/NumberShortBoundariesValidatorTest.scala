package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberShortBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberShortBoundariesValidator = new NumberShortBoundariesValidator

  val domain = "Number Boundaries"

  val minValue: Short = 6.8.toShort
  val maxValue: Short = 15.3.toShort

  val params: Map[String, String] = Map("minValue" -> minValue.toString, "maxValue" -> maxValue.toString)

  describe("***Number Short Boundaries Validator Test***") {

    it("Can Validate Number Too Short") {

      val number = 3.5.toShort

      val nel = numberShortBoundariesValidator.validate(locale, i18n, domain, number, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "number.short.boundaries.validator.number.is.too.short", List(domain, number.toString, minValue.toString)))
    }

    it("Can Validate Number Too Long") {

      val number = 45.0.toShort

      val nel = numberShortBoundariesValidator.validate(locale, i18n, domain, number, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "number.short.boundaries.validator.number.is.too.long", List(domain, number.toString, maxValue.toString)))
    }

  }

}