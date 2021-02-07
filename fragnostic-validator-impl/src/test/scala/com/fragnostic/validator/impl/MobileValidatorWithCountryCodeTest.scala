package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorWithCountryCodeTest extends AgnosticLifeCycleValidatorTest {

  describe("Mobile Validator With Country Code Test") {

    it("Can Validate Mobile With Country Code Test") {

      val mobile: String = " +55 11 9 5197 6773"
      val mobileRaw: String = "5511951976773"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, hasToFormat, msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode)
      validation.isSuccess should be(true)
      validation.toList.head should be(mobileRaw)

    }

  }

}
