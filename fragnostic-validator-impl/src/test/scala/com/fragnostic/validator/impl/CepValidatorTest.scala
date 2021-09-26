package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_NOT_VALID, Validated }
import scalaz.{ Failure, NonEmptyList, Success }

class CepValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CEP Validator Test***") {

    val domain = "CEP"
    val messages = Map(
      "cep.validator.cep.is.empty" -> msgCepIsEmpty,
      VALIDATOR_TEXT_NOT_VALID -> msgCepIsNotValid //
    )

    it("Can Validate CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val list = List("01414-000", "13214-206")

      list foreach (cep => {
        val validation: Validated[String] = cepValidator.validate(locale, domain, cep, params, messages)
        validation.isSuccess should be(true)
        validation.toList.head should be(cep)
      })

      val validation: Validated[String] = cepValidator.validate(locale, domain, "", params, messages)

      validation.isFailure should be(true)
      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgCepIsEmpty)

    }

  }

}