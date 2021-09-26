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
      val msgTooShort = validatorI18n.getFormattedString(locale, "number.float.boundaries.validator.number.is.too.short", List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        "number.float.boundaries.validator.number.is.too.short" -> msgTooShort)

      val nel = numberFloatBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(msgTooShort)
    }

    it("Can Validate Number Too Long") {

      val number = 45.0f
      val msgTooLong = validatorI18n.getFormattedString(locale, "number.float.boundaries.validator.number.is.too.long", List(domain, number.toString, maxValue.toString))
      val messages: Map[String, String] = Map(
        "number.float.boundaries.validator.number.is.too.long" -> msgTooLong)

      val nel = numberFloatBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        error => error,
        mistake => NonEmptyList((): Unit))

      nel should not be Nil
      nel.size should be(1)
      nel.head should be(msgTooLong)
    }

  }

}