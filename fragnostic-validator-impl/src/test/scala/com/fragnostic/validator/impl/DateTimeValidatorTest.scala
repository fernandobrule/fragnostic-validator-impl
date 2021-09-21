package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID, Validated }
import scalaz.NonEmptyList

class DateTimeValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("*** DateTime Validator Test ***") {

    val domain = "DateTime"
    val messages = Map(VALIDATOR_TEXT_EMPTY -> msgDateTimeIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgDateTimeIsNotValid)

    it("Can Validate Empty DateTime") {

      val dateTime = "    "

      val list = dateTimeValidator.validate(locale, validatorI18n, domain, dateTime, paramsEmpty, messages) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, "date.time.validator.date.time.is.empty"))
    }

    it("Can Validate Wrong DateTime") {

      val dateTime = "2020-10-03 23:50:0o"

      val list = dateTimeValidator.validate(locale, validatorI18n, domain, dateTime, paramsEmpty, messages) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, "date.time.validator.date.time.is.not.valid"))
    }

    it("Can Validate Right DateTime Case 1") {

      val dateTime = "2020-02-03 23:50:03"

      val validation: Validated[String] = dateTimeValidator.validate(locale, validatorI18n, domain, dateTime, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

    it("Can Validate Right DateTime Case 2") {

      val dateTime = "   2020-02-03     23:50:03   "

      val validation: Validated[String] = dateTimeValidator.validate(locale, validatorI18n, domain, dateTime, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

    it("Can Validate Right DateTime Case 3") {

      val dateTime = "2020-02-0323:50:03"

      val validation: Validated[String] = dateTimeValidator.validate(locale, validatorI18n, domain, dateTime, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03 23:50:03")
    }

  }
}
