package com.fragnostic.validator.impl

class DateNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "Date"

  describe("***DateNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty Date") {

      val date = "    "
      val mandatory = false

      assertResult(dateValidator.validate(locale, domain, date, paramsEmpty, Map(
        DATE_VALIDATOR_DATE_IS_EMPTY -> msgDateIsEmpty,
        DATE_VALIDATOR_DATE_IS_NOT_VALID -> msgDateIsNotValid), mandatory).isSuccess)(true)

    }

  }

}
