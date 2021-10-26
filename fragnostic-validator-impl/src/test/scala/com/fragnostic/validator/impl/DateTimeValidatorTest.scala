package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.NonEmptyList

class DateTimeValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("*** DateTime Validator Test ***") {

    val domain = "DateTime"
    val messages = Map(
      DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY -> msgDateTimeIsEmpty,
      DATE_TIME_VALIDATOR_DATE_TIME_IS_NOT_VALID -> msgDateTimeIsNotValid //
    )

    it("Can Validate Empty DateTime") {

      val dateTime = "    "

      val list = dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, messages) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY))
    }

    it("Can Validate Wrong DateTime") {

      val dateTime = "2020-10-03 23:50:0o"

      val list = dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, messages) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, DATE_TIME_VALIDATOR_DATE_TIME_IS_NOT_VALID))
    }

    it("Can Validate Right DateTime Case 1") {

      val dateTime = "2020-02-03 23:50:03"

      val validation: Validated[String] = dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

    it("Can Validate Right DateTime Case 2") {

      val dateTime = "   2020-02-03     23:50:03   "

      val validation: Validated[String] = dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

    it("Can Validate Right DateTime Case 3") {

      val dateTime = "2020-02-0323:50:03"

      val validation: Validated[String] = dateTimeValidator.validate(locale, domain, dateTime, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

  }
}
