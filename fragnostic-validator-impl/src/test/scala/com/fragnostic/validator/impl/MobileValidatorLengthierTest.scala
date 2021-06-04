package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorLengthierTest extends AgnosticLifeCycleValidatorTest {

  describe("***MobileValidatorLengthierTest***") {

    def validatorI18n = new ValidatorI18n()

    it("Can Validate Lengthier Mobile Number") {

      val maxLength = 16
      val params: Map[String, String] = Map(
        "maxLength" -> maxLength.toString,
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = "12345678901234567"

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, mobileValidatorMessages)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(validatorI18n.getFormattedString(locale, "text.max.length.validator.text.is.lengthier", List(mobile.length.toString, maxLength.toString)))

    }

  }

}
