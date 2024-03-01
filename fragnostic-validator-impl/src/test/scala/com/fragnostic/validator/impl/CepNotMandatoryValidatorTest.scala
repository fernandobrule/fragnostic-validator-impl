package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CepNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CepNotMandatoryValidatorTest***") {

    val domain = "CEP"
    val messages = Map(
      MSG_CEP_VALIDATOR_CEP_IS_EMPTY -> msgCepIsEmpty,
      MSG_CEP_VALIDATOR_CEP_IS_NOT_VALID -> msgCepIsNotValid //
    )

    it("Can Validate Not Mandatory Empty CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map(CONF_HAS_TO_FORMAT -> "true")
      val cep = ""
      val mandatory = false

      val validation: Validated[String] = cepValidator.validate(localePtBr, domain, cep, params, messages, mandatory)
      assertResult(validation.isSuccess)(true)
    }

    it("Can Validate Not Mandatory Wrong CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map(CONF_HAS_TO_FORMAT -> "true")
      val mandatory = false

      assertResult(cepValidator.validate(localePtBr, domain, "01414-000", params, messages, mandatory).isSuccess)(true)

      assertResult(cepValidator.validate(localePtBr, domain, "01414-00X", params, messages, mandatory).isSuccess)(false)
    }

  }

}