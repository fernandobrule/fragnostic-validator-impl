package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorFormatMobileTest extends AgnosticLifeCycleValidatorTest {

  describe("***Mobile Validator Format Mobile Test***") {

    it("Can Format Mobile With Country Code Test") {

      val params: Map[String, String] = Map(
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true")

      val mobile: String = " +55 11 9 5197 6773"
      val mobileFormated: String = "+55 (11) 951976773"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, List(msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode))

      validation.isSuccess should be(true)
      validation.toList.head should be(mobileFormated)

    }

    it("Can Format Mobile Without Country Code Test") {

      val params: Map[String, String] = Map(
        "hasToFormat" -> "true",
        "validateCountryCode" -> "false")

      val mobile: String = " 11 9 5197 6773"
      val mobileFormated: String = "(11) 951976773"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, List(msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode))

      validation.isSuccess should be(true)
      validation.toList.head should be(mobileFormated)

    }

    it("Can Validate Mobile With Country Code Without Format Test") {

      val params: Map[String, String] = Map(
        "hasToFormat" -> "false",
        "validateCountryCode" -> "true")

      val mobile: String = " +55 11 9 5197 6773"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, List(msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode))

      validation.isSuccess should be(true)
      validation.toList.head should be(mobile.replaceAll("\\s", "").replaceAll("\\+", ""))

    }

    it("Can Validate Mobile Without Country Code Without Format Test") {

      val params: Map[String, String] = Map(
        "hasToFormat" -> "false",
        "validateCountryCode" -> "false")

      val mobile: String = " 11 9 5197 6773"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, List(msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode))

      validation.isSuccess should be(true)
      validation.toList.head should be(mobile.replaceAll("\\s", ""))

    }

  }

}
