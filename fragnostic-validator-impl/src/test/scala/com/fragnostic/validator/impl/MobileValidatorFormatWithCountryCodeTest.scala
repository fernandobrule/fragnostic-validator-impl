package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorFormatWithCountryCodeTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Mobile Validator Format With Country Code Test ***") {

    it("Can Format Mobile With Country Code Test") {

      val domain = "Mobile"
      val mobile: String = " +55 11 9 5197 6773"
      val mobileFormated: String = "+55 (11) 951976773"

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, mobileValidatorParams, mobileValidatorMessages)

      validation.isSuccess should be(true)
      validation.toList.head should be(mobileFormated)

    }

  }

}
