package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorEmptyTest extends AgnosticLifeCycleValidatorTest {

  describe("Mobile Validator Empty Test") {

    val domain = "Mobile"

    it("Can Validate Empty Mobile") {

      val mobile: String = "  "

      val validation: Validated[String] = mobileValidator.validate(locale, validatorI18n, domain, mobile, mobileValidatorParams, mobileValidatorMessages)
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
