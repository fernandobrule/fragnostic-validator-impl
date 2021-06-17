package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***MobileNotMandatoryValidatorTest***") {

    val domain = "Mobile"

    it("Can Validate Empty Mobile") {

      val params: Map[String, String] = Map(
        "maxLength" -> "16",
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = "  "
      val mandatory = false

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, params, mobileValidatorMessages, mandatory)
      validation.isSuccess should be(true)

    }

  }

}
