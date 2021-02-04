package com.fragnostic.validator.impl

import scalaz.{ Failure, NonEmptyList, Success }

class EmailValidatorTest extends AgnosticLifeCycleValidatorTest with EmailValidator {

  val emptyTextMessage: String = "No ingresaste el Email"
  val errorMessage: String = "Email no válido"

  describe("Email Validator Test") {

    it("Can Validate Valid Email") {

      val email = "fernandobrule@gmail.com"

      val validation: Validated[String] = validate(email, hasToFormat = true, emptyTextMessage, errorMessage)

      validation.isSuccess should be(true)
      validation.toList.head should be(email)

    }

    it("Can Validate Non Valid Email") {

      val email = "fernandobrule#gmail.com"

      val validation: Validated[String] =
        validate(email, hasToFormat = true, emptyTextMessage, errorMessage)

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(errorMessage)

    }

  }

  it("Can Validate Empty Email") {

    val email: String = "  "
    val validation: Validated[String] = validate(email, hasToFormat = true, emptyTextMessage, errorMessage)

    validation.isFailure should be(true)

    (validation match {
      case Failure(f) =>
        f match {
          case NonEmptyList(a, value) => a
          case _ =>
        }
      case Success(s) =>
    }) should be(emptyTextMessage)

  }

}