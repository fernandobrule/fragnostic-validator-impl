package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID }

class DateNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "Date"

  describe("***DateNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty Date") {

      val date = "    "
      val mandatory = false

      dateValidator.validate(locale, validatorI18n, domain, date, paramsEmpty, Map(VALIDATOR_TEXT_EMPTY -> msgDateIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgDateIsNotValid), mandatory).isSuccess should be(true)

    }

  }

}
