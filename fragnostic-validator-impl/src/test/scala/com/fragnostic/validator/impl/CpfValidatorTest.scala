package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class CpfValidatorTest extends AgnosticLifeCycleValidatorTest {

  val cpfValidatorParams: Map[String, String] = Map(
    CONF_MIN_LENGTH -> "11",
    CONF_MAX_LENGTH -> "22" //
  )

  describe("Cpf Validator Test") {

    val domain = "CPF"

    it("Can Validate Valid CPF") {

      val cpfs: List[String] = List(
        "09241901160",
        "092.419.011-60",
        "17131749877",
        "171.317.498-77",
        "11144477735",
        "111.444.777-35" //
      )

      cpfs foreach (cpf => {
        val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, cpfValidatorParams, cpfValidatorMessages)
        assertResult(validation.isSuccess)(true)
        assertResult(validation.toList.head)(cpf)
      } //
      )

    }

    it("Can Validate Empty CPF") {

      val cpf = "  "
      val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, cpfValidatorParams, cpfValidatorMessages)
      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgCpfIsEmpty)
    }

    it("Can Validate Black List") {

      cpfValidator.BLACKLIST foreach (cpf => {
        val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, cpfValidatorParams, cpfValidatorMessages)
        assertResult(validation.isFailure)(true)

        assertResult((validation match {
          case Failure(f) =>
            f match {
              case NonEmptyList(a, value) => a
              case _ =>
            }
          case Success(s) =>
        }))(msgCpfIsNotValid)
      })
    }

    it("Can Validate Non Valid CPF") {

      val cpf = "uyuyiuyiu89789"

      val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, cpfValidatorParams, cpfValidatorMessages)
      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgCpfIsNotValid)
    }

  }

}
