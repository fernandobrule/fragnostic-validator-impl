package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID }

class DateTimeNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "DateTime"

  describe("***DateTimeNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty DateTime") {

      val dateTime = "    "
      val mandatory = false

      dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, Map(VALIDATOR_TEXT_EMPTY -> msgDateTimeIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgDateTimeIsNotValid), mandatory).isSuccess should be(true)

    }

  }

}
