package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class CpfNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CpfNotMandatoryValidatorTest***") {

    val params: Map[String, String] = Map("maxLength" -> "64")
    val domain = "CPF"

    it("Can Validate Not Mandatory Empty CPF") {

      val cpf = "  "
      val mandatory = false
      val validation: Validated[String] = cpfValidator.validate(locale, domain, cpf, params, cpfValidatorMessages, mandatory)
      validation.isSuccess should be(true)

    }

  }

}
