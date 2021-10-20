package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberLongBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberLongBoundariesValidator = new NumberLongBoundariesValidator

  val domain = "Number Boundaries"

  val minValue: Long = 6.8.toLong
  val maxValue: Long = 15.3.toLong

  val messages: Map[String, String] = Map(
    "number.long.boundaries.validator.number.is.too.short" -> validatorI18n.getString(locale, "number.long.boundaries.validator.number.is.too.short"),
    "number.long.boundaries.validator.number.is.too.long" -> validatorI18n.getString(locale, "number.long.boundaries.validator.number.is.too.long") //
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
      nel.head should be(validatorI18n.getFormattedString(locale, "number.long.boundaries.validator.number.is.too.short", List(domain, number.toString, minValue.toString)))
    }

    it("Can Validate Number Too Long") {

      val number = 45.0.toLong

      val nel = numberLongBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit) //
      )

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(validatorI18n.getFormattedString(locale, "number.long.boundaries.validator.number.is.too.long", List(domain, number.toString, maxValue.toString)))
    }

  }

}