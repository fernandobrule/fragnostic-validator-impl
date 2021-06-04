package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorNotValidMobileTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Mobile Validator Not Valid Mobile Test ***") {

    it("Can Validate Not Valid Mobile") {

      val params: Map[String, String] = Map(
        "maxLength" -> "16",
        "hasToFormat" -> "false",
        "validateCountryCode" -> "false",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = "ascsddsfds"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, mobileValidatorMessages)

      validation.isFailure should be(true)

      val msg = validation.toEither.fold(
        error => error.head,
        success => "ooops")

      msg should be(msgMobileNotValid)

    }

  }

}
