package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorLengthierTest extends AgnosticLifeCycleValidatorTest {

  describe("***MobileValidatorLengthierTest***") {

    def validatorI18n = new ValidatorI18n()
    val domain = "Mobile"

    it("Can Validate Lengthier Mobile Number") {

      val maxLength = "16"
      val params: Map[String, String] = Map(
        "maxLength" -> maxLength,
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = "12345678901234567"
      val msgMobileNotValidIsLenghtier: String = validatorI18n.getFormattedString(locale, "mobile.validator.mobile.is.too.long", List(mobile.length.toString, maxLength))
      val mobileValidatorMessages: List[String] = List(msgMobileIsEmpty, msgMobileIsNotValid, msgMobileNotValidIsLenghtier, msgMobileWithoutCountryCode)
      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, params, mobileValidatorMessages)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgMobileNotValidIsLenghtier)

    }

  }

}
