package com.fragnostic.validator.impl

import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorEmptyMobileTest extends AgnosticLifeCycleValidatorTest with MobileValidator {

  describe("Mobile Validator Empty Mobile Test") {

    it("Can Validate Empty Mobile") {

      val mobile: String = "  "
      val validation: StringValidation[String] = validateMobile(mobile, mobileValidatorEmptyTextErrorMessage, mobileValidatorWithoutCountryCodeErrorMessage, mobileValidatorGenericErrorMessage)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(mobileValidatorEmptyTextErrorMessage)

    }

  }

}
