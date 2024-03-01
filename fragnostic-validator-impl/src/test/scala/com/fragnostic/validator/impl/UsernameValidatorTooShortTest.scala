package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, Success }

class UsernameValidatorTooShortTest extends AgnosticLifeCycleValidatorTest {

  describe("*** UsernameValidatorTooShortTest ***") {

    it("Can Validate Username Too Short") {

      val username: String = "asda"
      val msgTooShort = validatorI18n.getFormattedString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT, List(username.length.toString, usernameMinLength))
      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> msgTooShort //
      )

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      assertResult(validation.isFailure)(true)

      assertResult(msgTooShort)(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

  }

}
