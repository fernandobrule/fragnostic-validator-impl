package com.fragnostic.validator.impl

class DateValidatorWithStrangeCharacterTest extends AgnosticLifeCycleValidatorTest {

  val domain = "Date"
  val params: Map[String, String] = Map(
    CONF_DATE_FORMAT -> "yyyy-MM-dd",
    CONF_MIN_LENGTH -> "6",
    CONF_MAX_LENGTH -> "16" //
  )

  def messages(domain: String): Map[String, String] = Map(
    MSG_STRING_VALIDATOR_STRING_IS_NULL -> msgDateIsNull,
    MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> msgDateIsEmpty,
    MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> msgDateIsTooLong(domain),
    MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID -> msgDateIsNotValid(domain) //
  )

  describe("*** DateValidatorWithStrangeCharacterTest ***") {

    it("Can Validate Wrong Date With an Strange Character") {

      val date = "2020-10-03 23:50:0o"
      val msgs = messages(domain)

      val message: String = dateValidator.validate(localePtBr, domain, date, params, msgs) fold (
        nel => nel.head,
        date => date //
      )

      assertResult(validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domain)))(message)

    }

  }

}
