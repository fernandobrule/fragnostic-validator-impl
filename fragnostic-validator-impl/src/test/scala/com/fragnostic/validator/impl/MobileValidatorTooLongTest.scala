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
      val msgMobileIsTooLong: String = validatorI18n.getString(localePtBr, MSG_MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG)
      val mobileValidatorMessages: Map[String, String] = Map(
        MSG_MOBILE_VALIDATOR_MOBILE_IS_EMPTY -> msgMobileIsEmpty,
        MSG_MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID -> msgMobileIsNotValid,
        MSG_MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG -> msgMobileIsTooLong,
        MSG_MOBILE_VALIDATOR_MOBILE_WITH_NOT_ALLOWED_COUNTRY_CODE -> msgMobileWithoutCountryCode //
      )

      val validation: Validated[String] = mobileValidator.validate(localePtBr, domain, mobile, mobileValidatorParams, mobileValidatorMessages)

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgMobileIsTooLong)

    }

  }

}
