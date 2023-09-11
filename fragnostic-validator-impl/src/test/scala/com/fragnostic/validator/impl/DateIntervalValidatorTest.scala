package com.fragnostic.validator.impl

class DateIntervalValidatorTest extends AgnosticLifeCycleValidatorTest {

  val domainStart = "DateStart"
  val domainEnd = "DateEnd"
  val domain = s"$domainStart;$domainEnd"

  describe("*** DateIntervalValidatorTest ***") {

    it("Can Validate Date Interval") {

      val dateStart = "03-02-2020"
      val dateEnd = "03-05-2020"

      val answer: (String, String) = dateIntervalValidator.validate(localePtBr, domain, (dateStart, dateEnd), paramsDateIntervalValidator, messagesDateIntervalValidator(domainStart, domainEnd)) fold (
        nel => throw new IllegalStateException(nel.list.toList.mkString(", ")),
        date => date //
      )

      assertResult((dateStart, dateEnd))(answer)

    }

    it("Can Validate Date Interval 2") {

      val dateStart = "03-02-2020"
      val dateEnd = "03-05-2020"
      val domain = s"$domainEnd"

      val answer: (String, String) = dateIntervalValidator.validate(localePtBr, domain, (dateStart, dateEnd), paramsDateIntervalValidator, messagesDateIntervalValidator(domainStart, domainEnd)) fold (
        nel => throw new IllegalStateException(nel.list.toList.mkString(", ")),
        date => date //
      )

      assertResult((dateStart, dateEnd))(answer)

    }

    it("Can Validate Wrong Date Interval") {

      val dateStart = "03-05-2020"
      val dateEnd = "03-02-2020"

      val answer: String = dateIntervalValidator.validate(localePtBr, domain, (dateStart, dateEnd), paramsDateIntervalValidator, messagesDateIntervalValidator(domainStart, domainEnd)) fold (
        nel => nel.list.toList.mkString(", "),
        date => "ooops" //
      )

      assertResult(validatorI18n.getString(localePtBr, MSG_DATE_INTERVAL_VALIDATOR_START_DATE_IS_AFTER_END_DATE))(answer)

    }

  }
}
