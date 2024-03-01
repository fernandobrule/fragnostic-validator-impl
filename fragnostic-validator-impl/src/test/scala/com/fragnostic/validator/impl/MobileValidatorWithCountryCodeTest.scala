package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorWithCountryCodeTest extends AgnosticLifeCycleValidatorTest {

  describe("***Mobile Validator With Country Code Test***") {

    val domain = "Mobile"

    it("Can Validate Mobile With Country Code Test") {

      val mobile: String = " +55 11 9 5197 6773"
      val mobileExpected: String = "+55 (11) 951976773"

      val mobileValidatorParams: Map[String, String] = Map(
        CONF_MIN_LENGTH -> "11",
        CONF_MAX_LENGTH -> "22",
        CONF_HAS_TO_FORMAT -> "true",
        CONF_VALIDATE_COUNTRY_CODE -> "true",
        CONF_COUNTRY_CODES_WHITE_LIST -> "54;55;56;598" //
      )

      val validation: Validated[String] = mobileValidator.validate(localePtBr, domain, mobile, mobileValidatorParams, mobileValidatorMessages)

      assertResult(true)(validation.isSuccess)
      assertResult(mobileExpected)(validation.toList.head)

    }

  }

}
