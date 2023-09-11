package com.fragnostic.validator.impl

class DateValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domain = "Date"
  val params: Map[String, String] = Map(
    CONF_DATE_FORMAT -> "yyyy-MM-dd",
    CONF_MIN_LENGTH -> "6",
    CONF_MAX_LENGTH -> "16" //
  )

  def messages(domain: String): Map[String, String] = Map(
    MSG_TEXT_VALIDATOR_TEXT_IS_NULL -> msgDateIsNull,
    MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY -> msgDateIsEmpty,
    MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG -> msgDateIsTooLong(domain),
    MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID -> msgDateIsNotValid(domain) //
  )

  describe("*** Date Validator Test ***") {

    it("Can Validate Empty Date") {

      val date = "    "

      val message: String = dateValidator.validate(localePtBr, domain, date, params, messages(domain)) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(validatorI18n.getString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_EMPTY))(message)
    }

    it("Can Validate Right Date With Spaces at Beginning and at the End") {

      val date = "   03-02-2020     "
      val params: Map[String, String] = Map(
        CONF_DATE_FORMAT -> "dd-MM-yyyy",
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> "16" //
      )

      val answer: String = dateValidator.validate(localePtBr, domain, date, params, messages(domain)) fold (
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

      val answer: String = dateValidator.validate(localePtBr, domain, date, params, messages(domain)) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domain)))(answer)

    }

  }
}
