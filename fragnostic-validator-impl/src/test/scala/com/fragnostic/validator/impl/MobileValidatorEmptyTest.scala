package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorEmptyTest extends AgnosticLifeCycleValidatorTest {

  describe("Mobile Validator Empty Test") {

    val domain = "Mobile"

    it("Can Validate Empty Mobile") {

      val params: Map[String, String] = Map(
        "maxLength" -> "16",
        "hasToFormat" -> "true",
        "validateCountryCode" -> "true",
        "countryCodesWhiteList" -> "54;55;56;598" //
      )

      val mobile: String = "  "

      val validation: Validated[String] = mobileValidator.validate(locale, domain, mobile, params, mobileValidatorMessages)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgMobileIsEmpty)

    }

  }

}
