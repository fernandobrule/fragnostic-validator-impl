package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated

class EmailNotMandatoryValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***EmailNotMandatoryValidatorTest***") {

    it("Can Validate Not Mandatory Empty Email") {

      val email = "  "
      val mandatory = false

      val validation: Validated[String] = emailValidator.validate(localePtBr, emailValidatorDomain, email, emailValidatorParams, emailValidatorMessages, mandatory)

      assertResult(validation.isSuccess)(true)

    }

  }

}
