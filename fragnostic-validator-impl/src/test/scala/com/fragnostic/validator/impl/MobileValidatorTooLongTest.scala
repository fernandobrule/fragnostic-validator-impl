package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorTooLongTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Mobile Validator Too Long Test ***") {

    def validatorI18n = new ValidatorI18n()
    val domain = "Mobile"

    it("Can Validate Mobile Number Too Long") {

      val mobile: String = "353453452345678901234567"
      val msgMobileIsTooLong: String = validatorI18n.getFormattedString(locale, MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG, List(mobile.length.toString, mobileValidatorParamMaxLength))
      val mobileValidatorMessages: Map[String, String] = Map(
        MOBILE_VALIDATOR_MOBILE_IS_EMPTY -> msgMobileIsEmpty,
        MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID -> msgMobileIsNotValid,
        MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG -> msgMobileIsTooLong,
        MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE -> msgMobileWithoutCountryCode //
      )

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, mobileValidatorParams, mobileValidatorMessages)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgMobileIsTooLong)

    }

  }

}
