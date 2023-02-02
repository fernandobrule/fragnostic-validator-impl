package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

import javax.swing.text.MaskFormatter

class CpfValidatorTest extends AgnosticLifeCycleValidatorTest {

  val STRICT_STRIP_REGEX: String = """[.\-]"""
  def strip(number: String): String = {
    number.trim.replaceAll(STRICT_STRIP_REGEX, "")
  }

  val maskCpf: MaskFormatter = {
    val instance = new MaskFormatter("###.###.###-##")
    instance.setValueContainsLiteralCharacters(false)
    instance
  }

  def formatCpf(cpf: String): String = {
    maskCpf.valueToString(strip(cpf))
  }

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

      cpfs foreach (
        cpf => {
          val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, Map.empty, cpfValidatorMessages)
          assertResult(true)(validation.isSuccess)
          val formattedCpf = formatCpf(cpf)
          assertResult(formattedCpf)(formatCpf(validation.toList.head))
        } //
      )
    }

    it("Can Validate Empty CPF") {

      val cpf = "  "
      val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, Map.empty, cpfValidatorMessages)
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
        val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, Map.empty, cpfValidatorMessages)
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

      val validation: Validated[String] = cpfValidator.validate(localePtBr, domain, cpf, Map.empty, cpfValidatorMessages)
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
