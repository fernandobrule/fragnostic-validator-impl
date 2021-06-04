package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorEmptyMobileTest extends AgnosticLifeCycleValidatorTest {

  describe("Mobile Validator Empty Mobile Test") {

    it("Can Validate Empty Mobile") {

      val mobile: String = "  "
      val validation: Validated[String] = mobileValidator.validate(mobile, locale, params, List(msgMobileEmpty, msgMobileNotValid, msgMobileWithoutCountryCode))
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgMobileEmpty)

    }

  }

}
