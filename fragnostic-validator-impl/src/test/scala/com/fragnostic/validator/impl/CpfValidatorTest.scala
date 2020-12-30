package com.fragnostic.validator.impl

import scalaz.{ Failure, NonEmptyList, Success }

class CpfValidatorTest extends AgnosticLifeCycleValidatorTest with CpfValidator {

  val emptyTextMessage: String = "No ingresaste el CPF"
  val errorMessage: String = "CPF no válido"

  describe("Cpf Validator Test") {

    it("Can Validate Valid CPF") {

      val cpf: String = "092419011160"

      val validation: StringValidation[String] = validateCpf(cpf, emptyTextMessage, errorMessage)
      validation.isSuccess should be(true)
      validation.toList.head should be(cpf)

    }

    it("Can Validate Empty CPF") {

      val cpf: String = ""

      val validation: StringValidation[String] = validateCpf(cpf, emptyTextMessage, errorMessage)

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

    it("Can Validate Non Valid CPF") {

      val cpf: String = "uyuyiuyiu89789"

      val validation: StringValidation[String] = validateCpf(cpf, emptyTextMessage, errorMessage)

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
