package com.fragnostic.validator.impl

class DateIntervalValidatorWronStartDateTest extends AgnosticLifeCycleValidatorTest {

  val domainStart = "DateStart"
  val domainEnd = "DateEnd"
  val domain = s"$domainStart;$domainEnd"

  it("Can Validate Wrong Date Start") {

    val dateStart = "2020-05-03"
    val dateEnd = "03-06-2020"

    val answer: String = dateIntervalValidator.validate(localePtBr, domain, (dateStart, dateEnd), paramsDateIntervalValidator, messagesDateIntervalValidator(domainStart, domainEnd)) fold (
      nel => nel.list.toList.mkString(", "),
      date => "ooops" //
    )

    assertResult(validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domainStart)))(answer)

  }

}
