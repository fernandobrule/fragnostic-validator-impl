package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class EmailValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("Email Validator Test") {

    it("Can Validate Valid Email") {

      val email = "fernandobrule@gmail.com"

      val validation: Validated[String] = emailValidator.validate(locale, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      validation.isSuccess should be(true)
      validation.toList.head should be(email)

    }

    it("Can Validate Non Valid Email") {

      val email = "fernandobrule#gmail.com"

      val validation: Validated[String] = emailValidator.validate(locale, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgEmailIsNotValid)

    }

    it("Can Validate Empty Email") {

      val email = "  "
      val validation: Validated[String] = emailValidator.validate(locale, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgEmailIsEmpty)

    }

    it("Can Validate Lengthier Email") {

      val email = "sdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsa@sdfasdfas.com"
      val messageLengthier = validatorI18n.getFormattedString(locale, "text.max.length.validator.text.is.too.long", List(email.length.toString, emailValidatorMaxLength))
      val messages: List[String] = List(msgEmailIsEmpty, msgEmailIsNotValid, messageLengthier)
      val validation: Validated[String] = emailValidator.validate(locale, emailValidatorDomain, email, emailValidatorParams, messages)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(messageLengthier)

    }

  }

}