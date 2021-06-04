package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CepValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("CEP Validator Test") {

    it("Can Validate Valid CEP") {

      val cepValidator = new CepValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")
      val list = List("01414-000", "13214-206")

      list foreach (cep => {
        val validation: Validated[String] = cepValidator.validate(cep, locale, params, List(msgCepEmpty, msgCepNotValid))
        validation.isSuccess should be(true)
        validation.toList.head should be(cep)
      })

    }

  }

}