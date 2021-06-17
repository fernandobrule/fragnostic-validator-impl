package com.fragnostic.validator.impl

import scalaz.NonEmptyList

class DateTimeNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "DateTime"

  describe("***DateTimeNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty DateTime") {

      val dateTime = "    "
      val mandatory = false

      dateTimeValidator.validate(locale, domain, dateTime, params, List(msgDateTimeIsEmpty, msgDateTimeIsNotValid), mandatory).isSuccess should be(true)

    }

  }

}
