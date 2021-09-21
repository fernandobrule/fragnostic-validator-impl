package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class EmailNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***EmailNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty Email") {

      val email = "  "
      val mandatory = false

      val validation: Validated[String] = emailValidator.validate(locale, validatorI18n, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages, mandatory)

      validation.isSuccess should be(true)

    }

  }

}
