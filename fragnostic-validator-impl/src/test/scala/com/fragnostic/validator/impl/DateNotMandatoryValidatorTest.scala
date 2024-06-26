package com.fragnostic.validator.impl

class DateNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***DateNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty Date") {

      val domain = "Date"
      val date = "    "
      val mandatory = false

      assertResult(dateValidator.validate(localePtBr, domain, date, paramsEmpty, Map(
        MSG_DATE_VALIDATOR_DATE_IS_EMPTY -> msgDateIsEmpty,
        MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID -> msgDateIsNotValid(domain)), mandatory).isSuccess)(true)

    }

  }

}
