package com.fragnostic.validator.impl

class MobileValidatorWithCountryCodeTest extends AgnosticLifeCycleValidatorTest with MobileValidator {

  describe("Mobile Validator With Country Code Test") {

    it("Can Validate Mobile With Country Code Test") {

      val mobile: String = " +55 11 9 5197 6773"
      val mobileRaw: String = "5511951976773"

      val validation: StringValidation[String] = validateMobile(mobile, mobileValidatorEmptyTextErrorMessage, mobileValidatorWithoutCountryCodeErrorMessage, mobileValidatorGenericErrorMessage)
      validation.isSuccess should be(true)
      validation.toList.head should be(mobileRaw)

    }

  }

}
