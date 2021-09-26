package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, Validated }

class CepNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CepNotMandatoryValidatorTest***") {

    val domain = "CEP"
    val messages = Map(
      VALIDATOR_TEXT_EMPTY -> msgCepIsEmpty,
      "cep.validator.cep.not.valid" -> msgCepIsNotValid //
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