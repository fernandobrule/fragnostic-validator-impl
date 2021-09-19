package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberFloatBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberFloatBoundariesValidator = new NumberFloatBoundariesValidator

  val domain = "Number Boundaries"

  val minValue = 6.8f
  val maxValue = 15.3f

  val params: Map[String, String] = Map("minValue" -> minValue.toString, "maxValue" -> maxValue.toString)

  describe("***Number Float Boundaries Validator Test***") {

    it("Can Validate Number Too Short") {

      val number = 3.5f

      val nel = numberFloatBoundariesValidator.validate(locale, i18n, domain, number, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "number.float.boundaries.validator.number.is.too.short", List(domain, number.toString, minValue.toString)))
    }

    it("Can Validate Number Too Long") {

      val number = 45.0f

      val nel = numberFloatBoundariesValidator.validate(locale, i18n, domain, number, params) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "number.float.boundaries.validator.number.is.too.long", List(domain, number.toString, maxValue.toString)))
    }

  }

}