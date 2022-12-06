package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CepValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CEP Validator Test***") {

    val domain = "CEP"
    val messages = Map(
      MSG_CEP_VALIDATOR_CEP_IS_EMPTY -> msgCepIsEmpty,
      MSG_CEP_VALIDATOR_CEP_IS_NOT_VALID -> msgCepIsNotValid //
    )

    it("Can Validate CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map(CONF_HAS_TO_FORMAT -> "true")
      val list = List("01414-000", "13214-206")

      list foreach (cep => {
        val validation: Validated[String] = cepValidator.validate(localePtBr, domain, cep, params, messages)
        assertResult(validation.isSuccess)(true)
        assertResult(validation.toList.head)(cep)
      })

      assertResult((cepValidator.validate(localePtBr, domain, "", params, messages) fold (
        errors => errors.head,
        cep => "ooooops, this is wrong") //
      ))(msgCepIsEmpty)

      assertResult((cepValidator.validate(localePtBr, domain, "", params, Map(MSG_CEP_VALIDATOR_CEP_IS_NOT_VALID -> msgCepIsNotValid)) fold (
        errors => errors.head,
        cep => "ooooops, this is wrong")))(s"message___${localePtBr}___${MSG_CEP_VALIDATOR_CEP_IS_EMPTY}___${domain}___is.not.available")

      assertResult((cepValidator.validate(localePtBr, domain, "01414-00", params, Map(MSG_CEP_VALIDATOR_CEP_IS_EMPTY -> msgCepIsEmpty)) fold (
        errors => errors.head,
        cep => "ooooops, this is wrong")))(s"message___${MSG_CEP_VALIDATOR_CEP_IS_NOT_VALID}___is.not.vailable")

    }

  }

}