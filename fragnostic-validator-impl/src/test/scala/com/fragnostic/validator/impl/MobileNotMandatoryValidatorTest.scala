package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***MobileNotMandatoryValidatorTest***") {

    val domain = "Mobile"

    it("Can Validate Empty Mobile") {

      val mobile: String = "  "
      val mandatory = false

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, mobileValidatorParams, mobileValidatorMessages, mandatory)
      assertResult(validation.isSuccess)(true)

    }

  }

}
