package com.fragnostic.validator.impl

class DateTimeNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "DateTime"

  describe("***DateTimeNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty DateTime") {

      val dateTime = "    "
      val mandatory = false

      assertResult(dateTimeValidator.validate(localePtBr, domain, dateTime, paramsEmpty, Map(
        MSG_DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY -> msgDateTimeIsEmpty,
        MSG_DATE_TIME_VALIDATOR_DATE_TIME_IS_NOT_VALID -> msgDateTimeIsNotValid), mandatory).isSuccess)(true)

    }

  }

}
