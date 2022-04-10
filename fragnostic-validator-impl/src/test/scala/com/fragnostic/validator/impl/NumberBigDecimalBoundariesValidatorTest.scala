package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class NumberBigDecimalBoundariesValidatorTest extends AgnosticLifeCycleValidatorTest {

  val numberBigDecimalBoundariesValidator = new NumberBigDecimalBoundariesValidator

  val domain = "Number Boundaries"

  val minValue: BigDecimal = 6.8f
  val maxValue: BigDecimal = 15.3f

  val params: Map[String, String] = Map("minValue" -> minValue.toString, "maxValue" -> maxValue.toString)

  describe("***Number BigDecimal Boundaries Validator Test***") {

    it("Can Validate Number The Right") {

      val number: BigDecimal = 10f
      val msgTooShort = validatorI18n.getFormattedString(locale, NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT -> msgTooShort //
      )

      val numberValidated: BigDecimal = numberBigDecimalBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        nel => throw new IllegalStateException(nel.list.toList.mkString("; ")),
        number => number //
      )

      assertResult(number)(numberValidated)
    }

    it("Can Validate Null Number") {

      val number: BigDecimal = null
      val msgIsNull = validatorI18n.getString(locale, NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_NULL)
      val messages: Map[String, String] = Map(
        NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_NULL -> msgIsNull //
      )

      val nel = numberBigDecimalBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        nel => nel,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(1)(nel.size)
      assertResult(msgIsNull)(nel.head)
    }

    it("Can Validate Number Too Short") {

      val number: BigDecimal = 3.5f
      val msgTooShort = validatorI18n.getFormattedString(locale, NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, List(domain, number.toString, minValue.toString))
      val messages: Map[String, String] = Map(
        NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_SHORT -> msgTooShort //
      )

      val nel = numberBigDecimalBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        nel => nel,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(1)(nel.size)
      assertResult(msgTooShort)(nel.head)
    }

    it("Can Validate Number Too Long") {

      val number: BigDecimal = 45.0f
      val msgTooLong = validatorI18n.getFormattedString(locale, NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG, List(domain, number.toString, maxValue.toString))
      val messages: Map[String, String] = Map(
        NUMBER_BIG_DECIMAL_BOUNDARIES_VALIDATOR_NUMBER_IS_TOO_LONG -> msgTooLong //
      )

      val nel = numberBigDecimalBoundariesValidator.validate(locale, domain, number, params, messages) fold (
        nel => nel,
        mistake => NonEmptyList((): Unit) //
      )

      assertResult(1)(nel.size)
      assertResult(msgTooLong)(nel.head)
    }

  }

}