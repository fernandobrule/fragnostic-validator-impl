package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, Success }

class UsernameValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("*** Username Validator Test ***") {

    it("Can Validate Username") {

      val username: String = "asdasdayyyy"

      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT) //
      )

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)
      assertResult(validation.isSuccess)(true)
      assertResult(validation.toList.head)(username)
    }

    it("Can Validate Username Empty") {

      val username: String = ""

      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT) //
      )

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      assertResult(validation.isFailure)(true)

      assertResult(validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY))(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Not Mandatory Empty") {

      val username: String = ""

      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT) //
      )

      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      assertResult(validation.isSuccess)(true)
    }

    it("Can Validate Username Not Mandatory Too Short") {

      val username: String = "asd"
      val msgTooShort = validatorI18n.getFormattedString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT, List(username.length.toString, usernameMinLength))
      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> msgTooShort //
      )

      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      assertResult(validation.isFailure)(true)

      assertResult(msgTooShort)(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Not Mandatory Too Long") {

      val username: String = "asdasfsadfasdfasdfasdfasdfsafsdsd"
      val msgTooLong = validatorI18n.getFormattedString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG, List(username.length.toString, usernameMaxLength))
      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG -> msgTooLong //
      )

      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      assertResult(validation.isFailure)(true)

      assertResult(msgTooLong)(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Too Long") {

      val username: String = "asdasfsadfasdfasdfasdfasdfsafsdsd"
      val msgTooLong = validatorI18n.getFormattedString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG, List(username.length.toString, usernameMaxLength))
      val messages: Map[String, String] = Map(
        MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(localePtBr, MSG_USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        MSG_USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG -> msgTooLong //
      )

      val validation: Validated[String] = usernameValidator.validate(localePtBr, usernameDomain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      assertResult(validation.isFailure)(true)

      assertResult(msgTooLong)(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

  }

}
