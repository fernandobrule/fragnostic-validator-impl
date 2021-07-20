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
      val msgMobileIsTooLong: String = validatorI18n.getFormattedString(locale, "mobile.validator.mobile.is.too.long", List(mobile.length.toString, mobileValidatorParamMaxLength))
      val mobileValidatorMessages: List[String] = List(
        msgMobileIsEmpty,
        msgMobileIsNotValid,
        msgMobileIsTooLong,
        msgMobileWithoutCountryCode //
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
