package com.fragnostic.validator.impl

class DeltaTimeValidatorTest extends AgnosticLifeCycleValidatorTest {

  def deltaTimeYears(years: Long): String = s"${years}y"
  def deltaTimeYearsMonths(years: Long, months: Long): String = s"${years}y ${months}m"
  def deltaTimeYearsMonthsWeeks(years: Long, months: Long, weeks: Long): String = s"${years}y ${months}m ${weeks}w"
  def deltaTimeMonths(months: Long): String = s"${months}m"
  def deltaTimeWeeks(weeks: Long): String = s"${weeks}w"
  def deltaTimeDays(days: Long): String = s"${days}d"

  val deltaTimeValidatorMessages: Map[String, String] = Map(
    MSG_STRING_VALIDATOR_STRING_IS_EMPTY -> validatorI18n.getString(localePtBr, "delta.time.validator.delta.time.is.empty"),
    MSG_STRING_VALIDATOR_STRING_IS_NULL -> validatorI18n.getString(localePtBr, "delta.time.validator.delta.time.is.empty"),
    MSG_STRING_VALIDATOR_STRING_IS_TOO_LONG -> validatorI18n.getString(localePtBr, "delta.time.validator.delta.time.is.too.long"),
    MSG_STRING_VALIDATOR_STRING_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, "delta.time.validator.delta.time.is.too.short"),
    MSG_DELTA_TIME_VALIDATOR_DELTA_TIME_IS_NOT_VALID -> validatorI18n.getString(localePtBr, "delta.time.validator.delta.time.is.not.valid") //
  )

  describe("*** DeltaTimeValidatorTest ***") {

    it("Can Validate Delta Time") {

      val deltaTime3days = deltaTimeDays(3)
      assertResult(deltaTime3days)(deltaTimeValidator.validate(localePtBr, "DeltaTime", deltaTime3days, Map.empty, deltaTimeValidatorMessages) fold (
        nel => nel.list.toList.mkString(", "),
        deltaTime => deltaTime //
      ))

      val deltaTimeBad = "asdasdsa"
      assertResult(validatorI18n.getString(localePtBr, "delta.time.validator.delta.time.is.not.valid"))(deltaTimeValidator.validate(localePtBr, "DeltaTime", deltaTimeBad, Map.empty, deltaTimeValidatorMessages) fold (
        nel => nel.list.toList.mkString(", "),
        deltaTime => deltaTime //
      ))

    }
  }

}
