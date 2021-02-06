package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class EmailValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("Email Validator Test") {

    it("Can Validate Valid Email") {

      val email = "fernandobrule@gmail.com"

      val validation: Validated[String] = emailValidator.validate(email, locale, hasToFormat, msgEmailEmpty, msgEmailNotValid)

      validation.isSuccess should be(true)
      validation.toList.head should be(email)

    }

    it("Can Validate Non Valid Email") {

      val email = "fernandobrule#gmail.com"

      val validation: Validated[String] = emailValidator.validate(email, locale, hasToFormat, msgEmailEmpty, msgEmailNotValid)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgEmailNotValid)

    }

  }

  it("Can Validate Empty Email") {

    val email = "  "
    val validation: Validated[String] = emailValidator.validate(email, locale, hasToFormat, msgEmailEmpty, msgEmailNotValid)

    validation.isFailure should be(true)

    (validation match {
      case Failure(f) =>
        f match {
          case NonEmptyList(a, value) => a
          case _ =>
        }
      case Success(s) =>
    }) should be(msgEmailEmpty)

  }

}