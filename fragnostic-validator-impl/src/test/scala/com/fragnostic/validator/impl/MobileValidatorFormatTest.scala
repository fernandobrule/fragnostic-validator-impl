package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class MobileValidatorFormatTest extends AgnosticLifeCycleValidatorTest {

  describe("***Mobile Validator Format Test***") {

    val domain = "Mobile"

    it("Can Format Mobile Without Country Code Test") {

      val mobile: String = " 11 9 5197 6773"
      val mobileValidatorParams: Map[String, String] = Map(
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> mobileValidatorParamMaxLength,
        CONF_HAS_TO_FORMAT -> "true",
        CONF_VALIDATE_COUNTRY_CODE -> "false",
        CONF_COUNTRY_CODES_WHITE_LIST -> "54;55;56;598" //
      )
      val mobileFormated: String = "(11) 951976773"

      val validation: Validated[String] = mobileValidator.validate(localePtBr, domain, mobile, mobileValidatorParams, mobileValidatorMessages)
      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(mobileFormated)

    }

    it("Can Validate Mobile With Country Code Without Format Test") {

      val params: Map[String, String] = Map(
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> "32",
        CONF_HAS_TO_FORMAT -> "false",
        CONF_VALIDATE_COUNTRY_CODE -> "true",
        CONF_COUNTRY_CODES_WHITE_LIST -> "54;55;56;598" //
      )

      val mobile: String = " +55 11 9 5197 6773"

      val validation: Validated[String] = mobileValidator.validate(localePtBr, domain, mobile, params, mobileValidatorMessages)

      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(mobile.replaceAll("\\s", "").replaceAll("\\+", ""))

    }

    it("Can Validate Mobile Without Country Code Without Format Test") {

      val params: Map[String, String] = Map(
        CONF_MIN_LENGTH -> "6",
        CONF_MAX_LENGTH -> "16",
        CONF_HAS_TO_FORMAT -> "false",
        CONF_VALIDATE_COUNTRY_CODE -> "false",
        CONF_COUNTRY_CODES_WHITE_LIST -> "54;55;56;598" //
      )

      val mobile: String = " 11 9 5197 6773"

      val validation: Validated[String] = mobileValidator.validate(localePtBr, domain, mobile, params, mobileValidatorMessages)

      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(mobile.replaceAll("\\s", ""))

    }

  }

}
