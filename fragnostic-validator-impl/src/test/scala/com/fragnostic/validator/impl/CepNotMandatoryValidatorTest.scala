package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CepNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CepNotMandatoryValidatorTest***") {

    val domain = "CEP"
    val messages = Map(
      CEP_VALIDATOR_CEP_IS_EMPTY -> msgCepIsEmpty,
      CEP_VALIDATOR_CEP_IS_NOT_VALID -> msgCepIsNotValid //
    )

    it("Can Validate Not Mandatory Empty CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val cep = ""
      val mandatory = false

      val validation: Validated[String] = cepValidator.validate(locale, domain, cep, params, messages, mandatory)
      validation.isSuccess should be(true)
    }

    it("Can Validate Not Mandatory Wrong CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val mandatory = false

      cepValidator.validate(locale, domain, "01414-000", params, messages, mandatory).isSuccess should be(true)

      cepValidator.validate(locale, domain, "01414-00X", params, messages, mandatory).isSuccess should be(false)
    }

  }

}