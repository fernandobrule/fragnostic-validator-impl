package com.fragnostic.validator.impl

import scalaz.{ Failure, NonEmptyList, Success }

class CpfValidatorTest extends AgnosticLifeCycleValidatorTest with CpfValidator {

  val emptyTextMessage: String = "No ingresaste el CPF"
  val errorMessage: String = "CPF no válido"

  describe("Cpf Validator Test") {

    it("Can Validate Valid CPF") {

      val cpfs: List[String] = List(
        "09241901160",
        "092.419.011-60",
        "17131749877",
        "171.317.498-77",
        "11144477735",
        "111.444.777-35")

      cpfs foreach (cpf => {
        val validation: Validated[String] = validate(cpf, hasToFormat = true, emptyTextMessage, errorMessage)
        validation.isSuccess should be(true)
        validation.toList.head should be(cpf)
      })
    }

    it("Can Validate Empty CPF") {

      val cpf: String = "  "
      val validation: Validated[String] = validate(cpf, hasToFormat = true, emptyTextMessage, errorMessage)
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

    it("Can Validate Black List") {

      BLACKLIST foreach (cpf => {
        val validation: Validated[String] = validate(cpf, hasToFormat = true, emptyTextMessage, errorMessage)
        validation.isFailure should be(true)

        (validation match {
          case Failure(f) =>
            f match {
              case NonEmptyList(a, value) => a
              case _ =>
            }
          case Success(s) =>
        }) should be(errorMessage)
      })
    }

    it("Can Validate Non Valid CPF") {

      val cpf: String = "uyuyiuyiu89789"

      val validation: Validated[String] = validate(cpf, hasToFormat = true, emptyTextMessage, errorMessage)
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

}
