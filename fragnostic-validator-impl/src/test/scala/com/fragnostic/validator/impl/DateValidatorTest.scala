package com.fragnostic.validator.impl

class DateValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "Date"
  val params: Map[String, String] = Map(
    CONF_DATE_FORMAT -> "yyyy-MM-dd",
    CONF_MIN_LENGTH -> "6",
    CONF_MAX_LENGTH -> "16" //
  )
  val messages = Map(
    MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_NULL -> msgDateIsNull,
    MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_EMPTY -> msgDateIsEmpty,
    MSG_TEXT_BOUNDARIES_VALIDATOR_TEXT_IS_TOO_LONG -> msgDateIsTooLong,
    MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID -> msgDateIsNotValid //
  )

  describe("*** Date Validator Test ***") {

    it("Can Validate Empty Date") {

      val date = "    "

      val message: String = dateValidator.validate(localePtBr, domain, date, params, messages) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(validatorI18n.getString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_EMPTY))(message)
    }

    it("Can Validate Wrong Date With an Strange Character") {

      val date = "2020-10-03 23:50:0o"

      val message: String = dateValidator.validate(localePtBr, domain, date, params, messages) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(validatorI18n.getString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID))(message)
    }

    it("Can Validate Right Date With Format Default") {

      val date = "02/03/2022"
      val params: Map[String, String] = Map(
        // dont DATE_FORMAT -> "yyyy-MM-dd",
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> "16" //
      )

      val answer: String = dateValidator.validate(localePtBr, domain, date, params, messages) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(date)(answer)
    }

    it("Can Validate Right Date With Spaces at Beginning and at the End") {

      val date = "   03-02-2020     "
      val params: Map[String, String] = Map(
        CONF_DATE_FORMAT -> "dd-MM-yyyy",
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> "16" //
      )

      val answer: String = dateValidator.validate(localePtBr, domain, date, params, messages) fold (
        nel => nel.head,
        date => date //
      )

      assertResult("03-02-2020")(answer)
    }

    it("Can Validate Wrong Date") {

      val date = "03u02-2020"
      val params: Map[String, String] = Map(
        CONF_DATE_FORMAT -> "dd-MM-yyyy",
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> "16" //
      )

      val answer: String = dateValidator.validate(localePtBr, domain, date, params, messages) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(validatorI18n.getString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID))(answer)

    }

  }
}
