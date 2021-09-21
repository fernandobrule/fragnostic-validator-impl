package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ VALIDATOR_TEXT_EMPTY, VALIDATOR_TEXT_NOT_VALID, Validated }

class CpfNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***CpfNotMandatoryValidatorTest***") {

    val params: Map[String, String] = Map("maxLength" -> "64")
    val domain = "CPF"

    it("Can Validate Not Mandatory Empty CPF") {

      val cpf = "  "
      val mandatory = false
      val validation: Validated[String] = cpfValidator.validate(locale, validatorI18n, domain, cpf, params, Map(VALIDATOR_TEXT_EMPTY -> msgCpfIsEmpty, VALIDATOR_TEXT_NOT_VALID -> msgCpfIsNotValid), mandatory)
      validation.isSuccess should be(true)

    }

  }

}
