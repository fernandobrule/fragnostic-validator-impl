package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CepNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CepNotMandatoryValidatorTest***") {

    val domain = "CEP"

    it("Can Validate Not Mandatory Empty CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val cep = ""
      val mandatory = false

      val validation: Validated[String] = cepValidator.validate(locale, domain, cep, params, List(msgCepIsEmpty, msgCepIsNotValid), mandatory)
      validation.isSuccess should be(true)

    }

    it("Can Validate Not Mandatory Wrong CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val mandatory = false

      cepValidator.validate(locale, domain, "01414-000", params, List(msgCepIsEmpty, msgCepIsNotValid), mandatory).isSuccess should be(true)

      cepValidator.validate(locale, domain, "01414-00X", params, List(msgCepIsEmpty, msgCepIsNotValid), mandatory).isSuccess should be(false)
    }

  }

}