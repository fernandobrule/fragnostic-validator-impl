package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID, Validated }

class CepNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CepNotMandatoryValidatorTest***") {

    val domain = "CEP"
    val messages = Map(VALIDATOR_TEXT_EMPTY -> msgCepIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgCepIsNotValid)

    it("Can Validate Not Mandatory Empty CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val cep = ""
      val mandatory = false

      val validation: Validated[String] = cepValidator.validate(locale, validatorI18n, domain, cep, params, messages, mandatory)
      validation.isSuccess should be(true)

    }

    it("Can Validate Not Mandatory Wrong CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val mandatory = false

      cepValidator.validate(locale, validatorI18n, domain, "01414-000", params, messages, mandatory).isSuccess should be(true)

      cepValidator.validate(locale, validatorI18n, domain, "01414-00X", params, messages, mandatory).isSuccess should be(false)
    }

  }

}