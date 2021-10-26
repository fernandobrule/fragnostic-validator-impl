package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, Success }

class UsernameValidatorTest extends AgnosticLifeCycleValidatorTest {

  def usernameValidator = new UsernameValidator()

  val domain: String = "Username"
  val username: String = "asdasdayyyy"
  val usernameMinLength = "8"
  val usernameMaxLength = "18"
  val usernameValidatorParams: Map[String, String] = Map(
    "minLength" -> usernameMinLength,
    "maxLength" -> usernameMaxLength //
  )

  val usernameValidatorMandatory: Boolean = true

  describe("*** Username Validator Test ***") {

    it("Can Validate Username") {

      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT) //
      )

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)
      validation.isSuccess should be(true)
      validation.toList.head should be(username)
    }

    it("Can Validate Username Empty") {

      val username: String = ""

      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT) //
      )

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY) should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Not Mandatory Empty") {

      val username: String = ""

      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT) //
      )

      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      validation.isSuccess should be(true)
    }

    it("Can Validate Username Not Mandatory Too Short") {

      val username: String = "asd"
      val msgTooShort = validatorI18n.getFormattedString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT, List(username.length.toString, usernameMinLength))
      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> msgTooShort //
      )

      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      msgTooShort should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Too Short") {

      val username: String = "asda"
      val msgTooShort = validatorI18n.getFormattedString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT, List(username.length.toString, usernameMinLength))
      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_SHORT -> msgTooShort //
      )

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      msgTooShort should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Not Mandatory Too Long") {

      val username: String = "asdasfsadfasdfasdfasdfasdfsafsdsd"
      val msgTooLong = validatorI18n.getFormattedString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG, List(username.length.toString, usernameMaxLength))
      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG -> msgTooLong //
      )

      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      msgTooLong should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Too Long") {

      val username: String = "asdasfsadfasdfasdfasdfasdfsafsdsd"
      val msgTooLong = validatorI18n.getFormattedString(locale, USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG, List(username.length.toString, usernameMaxLength))
      val messages: Map[String, String] = Map(
        USERNAME_VALIDATOR_USERNAME_IS_EMPTY -> validatorI18n.getString(locale, USERNAME_VALIDATOR_USERNAME_IS_EMPTY),
        USERNAME_VALIDATOR_USERNAME_IS_TOO_LONG -> msgTooLong //
      )

      val validation: Validated[String] = usernameValidator.validate(locale, domain, username, usernameValidatorParams, messages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      msgTooLong should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

  }

}
