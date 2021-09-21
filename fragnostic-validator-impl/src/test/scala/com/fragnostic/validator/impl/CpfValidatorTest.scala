package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID, Validated }
import scalaz.{ Failure, NonEmptyList, Success }

class CpfValidatorTest extends AgnosticLifeCycleValidatorTest {

  val cpfValidatorParams: Map[String, String] = Map("minLength" -> "11", "maxLength" -> "22")
  val messages = Map(VALIDATOR_TEXT_EMPTY -> msgCpfIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgCpfIsNotValid)

  describe("Cpf Validator Test") {

    val domain = "CPF"

    it("Can Validate Valid CPF") {

      val cpfs: List[String] = List(
        "09241901160",
        "092.419.011-60",
        "17131749877",
        "171.317.498-77",
        "11144477735",
        "111.444.777-35")

      cpfs foreach (cpf => {
        val validation: Validated[String] = cpfValidator.validate(locale, validatorI18n, domain, cpf, cpfValidatorParams, messages)
        validation.isSuccess should be(true)
        validation.toList.head should be(cpf)
      })
    }

    it("Can Validate Empty CPF") {

      val cpf = "  "
      val validation: Validated[String] = cpfValidator.validate(locale, validatorI18n, domain, cpf, cpfValidatorParams, messages)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgCpfIsEmpty)
    }

    it("Can Validate Black List") {

      cpfValidator.BLACKLIST foreach (cpf => {
        val validation: Validated[String] = cpfValidator.validate(locale, validatorI18n, domain, cpf, cpfValidatorParams, messages)
        validation.isFailure should be(true)

        (validation match {
          case Failure(f) =>
            f match {
              case NonEmptyList(a, value) => a
              case _ =>
            }
          case Success(s) =>
        }) should be(msgCpfIsNotValid)
      })
    }

    it("Can Validate Non Valid CPF") {

      val cpf = "uyuyiuyiu89789"

      val validation: Validated[String] = cpfValidator.validate(locale, validatorI18n, domain, cpf, cpfValidatorParams, messages)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgCpfIsNotValid)
    }

  }

}
