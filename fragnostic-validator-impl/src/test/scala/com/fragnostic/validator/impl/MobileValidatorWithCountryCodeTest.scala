package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorWithCountryCodeTest extends AgnosticLifeCycleValidatorTest {

  describe("***Mobile Validator With Country Code Test***") {

    it("Can Validate Mobile With Country Code Test") {

      val mobile: String = " +55 11 9 5197 6773"
      val mobileRaw: String = "+55 (11) 951976773"
      val params: Map[String, String] = Map(
        "maxLength" -> "32",
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, mobileValidatorMessages)

      validation.isSuccess should be(true)
      validation.toList.head should be(mobileRaw)

    }

  }

}
