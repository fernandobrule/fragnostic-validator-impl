package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class CpfValidatorTest extends AgnosticLifeCycleValidatorTest {

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
        val validation: Validated[String] = cpfValidator.validate(cpf, locale, hasToFormat, msgCpfEmpty, msgCpfNotValid)
        validation.isSuccess should be(true)
        validation.toList.head should be(cpf)
      })
    }

    it("Can Validate Empty CPF") {

      val cpf = "  "
      val validation: Validated[String] = cpfValidator.validate(cpf, locale, hasToFormat, msgCpfEmpty, msgCpfNotValid)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgCpfEmpty)
    }

    it("Can Validate Black List") {

      cpfValidator.BLACKLIST foreach (cpf => {
        val validation: Validated[String] = cpfValidator.validate(cpf, locale, hasToFormat, msgCpfEmpty, msgCpfNotValid)
        validation.isFailure should be(true)

        (validation match {
          case Failure(f) =>
            f match {
              case NonEmptyList(a, value) => a
              case _ =>
            }
          case Success(s) =>
        }) should be(msgCpfNotValid)
      })
    }

    it("Can Validate Non Valid CPF") {

      val cpf = "uyuyiuyiu89789"

      val validation: Validated[String] = cpfValidator.validate(cpf, locale, hasToFormat, msgCpfEmpty, msgCpfNotValid)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgCpfNotValid)
    }

  }

}
