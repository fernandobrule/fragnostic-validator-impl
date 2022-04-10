package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorFormatTest extends AgnosticLifeCycleValidatorTest {

  describe("***Mobile Validator Format Test***") {

    val domain = "Mobile"

    it("Can Format Mobile Without Country Code Test") {

      val mobile: String = " 11 9 5197 6773"
      val mobileValidatorParams: Map[String, String] = Map(
        "minLength" -> "6",
        "maxLength" -> mobileValidatorParamMaxLength,
        "hasToFormat" -> "true",
        "validateCountryCode" -> "false",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )
      val mobileFormated: String = "(11) 951976773"

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, mobileValidatorParams, mobileValidatorMessages)
      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(mobileFormated)

    }

    it("Can Validate Mobile With Country Code Without Format Test") {

      val params: Map[String, String] = Map(
        "minLength" -> "6",
        "maxLength" -> "32",
        "hasToFormat" -> "false",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = " +55 11 9 5197 6773"

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, params, mobileValidatorMessages)

      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(mobile.replaceAll("\\s", "").replaceAll("\\+", ""))

    }

    it("Can Validate Mobile Without Country Code Without Format Test") {

      val params: Map[String, String] = Map(
        "minLength" -> "6",
        "maxLength" -> "16",
        "hasToFormat" -> "false",
        "validateCountryCode" -> "false",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = " 11 9 5197 6773"

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, params, mobileValidatorMessages)

      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(mobile.replaceAll("\\s", ""))

    }

  }

}
