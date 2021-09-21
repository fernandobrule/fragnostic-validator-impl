package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID, Validated }
import scalaz.NonEmptyList

class DateValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Date Validator Test ***") {

    val domain = "Date"
    val messages = Map(VALIDATOR_TEXT_EMPTY -> msgDateIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgDateIsNotValid)

    it("Can Validate Empty Date") {

      val date = "    "

      val list = dateValidator.validate(locale, validatorI18n, domain, date, paramsEmpty, messages) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, "date.time.validator.date.time.is.empty"))
    }

    it("Can Validate Wrong Date") {

      val date = "2020-10-03 23:50:0o"

      val list = dateValidator.validate(locale, validatorI18n, domain, date, paramsEmpty, messages) fold (
        errors => errors,
        signupReq => NonEmptyList((): Unit))

      list should not be Nil
      list.size should be(1)
      list.head should be(validatorI18n.getString(locale, "date.time.validator.date.time.is.not.valid"))
    }

    it("Can Validate Right Date Case 1") {

      val date = "2020-02-03"

      val validation: Validated[String] = dateValidator.validate(locale, validatorI18n, domain, date, paramsEmpty, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("2020-02-03")
    }

    it("Can Validate Right Date Case 2") {

      val params: Map[String, String] = Map(dateValidator.DATE_REGEX -> """\s*(\d{2}-\d{2}-\d{4})\s*""")

      val date = "   03-02-2020     "

      val validation: Validated[String] = dateValidator.validate(locale, validatorI18n, domain, date, params, messages)
      validation.isSuccess should be(true)
      validation.toList.head should be("03-02-2020")
    }

  }
}
