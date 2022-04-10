package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CepValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CEP Validator Test***") {

    val domain = "CEP"
    val messages = Map(
      CEP_VALIDATOR_CEP_IS_EMPTY -> msgCepIsEmpty,
      CEP_VALIDATOR_CEP_IS_NOT_VALID -> msgCepIsNotValid //
    )

    it("Can Validate CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val list = List("01414-000", "13214-206")

      list foreach (cep => {
        val validation: Validated[String] = cepValidator.validate(locale, domain, cep, params, messages)
        assertResult(validation.isSuccess)(true)
        assertResult(validation.toList.head)(cep)
      })

      assertResult((cepValidator.validate(locale, domain, "", params, messages) fold (
        errors => errors.head,
        cep => "ooooops, this is wrong") //
      ))(msgCepIsEmpty)

      assertResult((cepValidator.validate(locale, domain, "", params, Map(CEP_VALIDATOR_CEP_IS_NOT_VALID -> msgCepIsNotValid)) fold (
        errors => errors.head,
        cep => "ooooops, this is wrong")))(s"message___${CEP_VALIDATOR_CEP_IS_EMPTY}___is.not.available")

      assertResult((cepValidator.validate(locale, domain, "01414-00", params, Map(CEP_VALIDATOR_CEP_IS_EMPTY -> msgCepIsEmpty)) fold (
        errors => errors.head,
        cep => "ooooops, this is wrong")))(s"message___${CEP_VALIDATOR_CEP_IS_NOT_VALID}___is.not.vailable")

    }

  }

}