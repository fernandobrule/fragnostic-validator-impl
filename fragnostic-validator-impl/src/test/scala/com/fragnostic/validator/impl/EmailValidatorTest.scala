package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class EmailValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("Email Validator Test") {

    it("Can Validate Valid Email") {

      val email = "fernandobrule@gmail.com"

      val validation: Validated[String] = emailValidator.validate(localePtBr, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(email)

    }

    it("Can Validate Non Valid Email") {

      val email = "fernandobrule#gmail.com"

      val validation: Validated[String] = emailValidator.validate(localePtBr, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgEmailIsNotValid)

    }

    it("Can Validate Empty Email") {

      val email = "  "
      val validation: Validated[String] = emailValidator.validate(localePtBr, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgEmailIsEmpty)

    }

    it("Can Validate Email Too Long") {

      val email = "sdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsa@sdfasdfas.com"
      val messageEmailTooLong = validatorI18n.getString(localePtBr, MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG)
      val validation: Validated[String] = emailValidator.validate(localePtBr, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(messageEmailTooLong)

    }

  }

}