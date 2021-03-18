package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorNotValidMobileTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Mobile Validator Not Valid Mobile Test ***") {

    it("Can Validate Not Valid Mobile") {

      val mobile: String = "ascsddsfds"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, hasToFormat, msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode)

      validation.isFailure should be(true)

      val msg = validation.toEither.fold(
        error => error.head,
        success => "ooops")

      msg should be(msgMobileNotValid)

    }

  }

}
