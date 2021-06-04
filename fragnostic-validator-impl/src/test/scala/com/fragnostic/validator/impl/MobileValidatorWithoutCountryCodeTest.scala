package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorWithoutCountryCodeTest extends AgnosticLifeCycleValidatorTest {

  describe("Mobile Validator Without Country Code Test") {

    it("Can Validate Mobile Without Country Code Test") {

      val mobile: String = " (11) 9 5197 6773   "
      val params: Map[String, String] = Map(
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true")

      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, List(msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode))

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
