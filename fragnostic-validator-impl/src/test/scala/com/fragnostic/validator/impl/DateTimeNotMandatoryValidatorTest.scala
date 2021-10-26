package com.fragnostic.validator.impl

class DateTimeNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "DateTime"

  describe("***DateTimeNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty DateTime") {

      val dateTime = "    "
      val mandatory = false

      dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, Map(
        DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY -> msgDateTimeIsEmpty,
        DATE_TIME_VALIDATOR_DATE_TIME_IS_NOT_VALID -> msgDateTimeIsNotValid), mandatory).isSuccess should be(true)

    }

  }

}
