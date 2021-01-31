package com.fragnostic.validator.impl

import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorWithoutCountryCodeTest extends AgnosticLifeCycleValidatorTest with MobileValidator {

  describe("Mobile Validator Without Country Code Test") {

    it("Can Validate Mobile Without Country Code Test") {

      val mobile: String = " (11) 9 5197 6773   "
      val mobileRaw: String = "11951976773"

      val validation: StringValidation[String] = validateMobile(mobile, mobileValidatorEmptyTextErrorMessage, mobileValidatorWithoutCountryCodeErrorMessage, mobileValidatorGenericErrorMessage)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(mobileValidatorWithoutCountryCodeErrorMessage)

    }

  }

}
