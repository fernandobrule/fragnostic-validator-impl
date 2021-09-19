package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorNotValidMobileTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Mobile Validator Not Valid Mobile Test ***") {

    val domain = ""

    it("Can Validate Not Valid Mobile") {

      val mobile: String = "ascsddsfds"

      val validation: Validated[String] = mobileValidator.validate(locale, i18n, domain, mobile, mobileValidatorParams, mobileValidatorMessages)

      validation.isFailure should be(true)

      val msg = validation.toEither.fold(
        error => error.head,
        success => "ooops")

      msg should be(msgMobileIsNotValid)

    }

  }

}
