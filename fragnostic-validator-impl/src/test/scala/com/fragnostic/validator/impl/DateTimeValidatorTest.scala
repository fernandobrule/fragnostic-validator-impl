package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.NonEmptyList

class DateTimeValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("*** DateTime Validator Test ***") {

    it("Can Validate Wrong Number Of Messages lt") {

      val dateTime = "    "

      val list = dateTimeValidator.validate(dateTime, locale, hasToFormat) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be("date.time.validator.wrong.number.of.messages")
    }

    it("Can Validate Wrong Number Of Messages gt") {

      val dateTime = "    "

      val list = dateTimeValidator.validate(dateTime, locale, hasToFormat, "kjklj", "fghfdhgdf", "sdfsadfsa", "fergdsf") fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be("date.time.validator.wrong.number.of.messages")
    }

    it("Can Validate Empty DateTime") {

      val dateTime = "    "

      val list = dateTimeValidator.validate(dateTime, locale, hasToFormat, msgDateTimeEmpty, msgDateTimeNotValid) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, "date.time.validator.date.time.is.empty"))
    }

    it("Can Validate Wrong DateTime") {

      val dateTime = "2020-10-03 23:50:0o"

      val list = dateTimeValidator.validate(dateTime, locale, hasToFormat, msgDateTimeEmpty, msgDateTimeNotValid) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, "date.time.validator.date.time.not.valid"))
    }

    it("Can Validate Right DateTime Case 1") {

      val dateTime = "2020-02-03 23:50:03"

      val validation: Validated[String] = dateTimeValidator.validate(dateTime, locale, hasToFormat, msgDateTimeEmpty, msgDateTimeNotValid)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

    it("Can Validate Right DateTime Case 2") {

      val dateTime = "   2020-02-03     23:50:03   "

      val validation: Validated[String] = dateTimeValidator.validate(dateTime, locale, hasToFormat, msgDateTimeEmpty, msgDateTimeNotValid)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

    it("Can Validate Right DateTime Case 3") {

      val dateTime = "2020-02-0323:50:03"

      val validation: Validated[String] = dateTimeValidator.validate(dateTime, locale, hasToFormat, msgDateTimeEmpty, msgDateTimeNotValid)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

  }
}
