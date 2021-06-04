package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorWithoutCountryCodeTest extends AgnosticLifeCycleValidatorTest {

  describe("Mobile Validator Without Country Code Test") {

    it("Can Validate Mobile Without Country Code Test") {

      val mobile: String = " (11) 9 5197 6773   "
      val params: Map[String, String] = Map(
        "maxLength" -> "16",
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, mobileValidatorMessages)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgMobileWithoutCountryCode)

    }

  }

}
