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
    "maxLength" -> usernameMaxLength)
  val usernameValidatorMessages: Map[String, String] = Map.empty
  val usernameValidatorMandatory: Boolean = true

  describe("*** Username Validator Test ***") {

    it("Can Validate Username") {
      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)
      validation.isSuccess should be(true)
      validation.toList.head should be(username)
    }

    it("Can Validate Username Empty") {

      val username: String = ""

      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      validatorI18n.getString(locale, "username.validator.username.is.empty") should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Not Mandatory Empty") {

      val username: String = ""
      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)

      validation.isSuccess should be(true)
    }

    it("Can Validate Username Not Mandatory Too Short") {

      val username: String = "asd"
      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      validatorI18n.getFormattedString(locale, "username.validator.username.is.too.short", List(username.length.toString, usernameMinLength)) should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Too Short") {

      val username: String = "asda"

      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      validatorI18n.getFormattedString(locale, "username.validator.username.is.too.short", List(username.length.toString, usernameMinLength)) should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Not Mandatory Too Long") {

      val username: String = "asdasfsadfasdfasdfasdfasdfsafsdsd"
      val usernameValidatorMandatory = false

      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      validatorI18n.getFormattedString(locale, "username.validator.username.is.too.long", List(username.length.toString, usernameMaxLength)) should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

    it("Can Validate Username Too Long") {

      val username: String = "asdasfsadfasdfasdfasdfasdfsafsdsd"

      val validation: Validated[String] = usernameValidator.validate(locale, validatorI18n, domain, username, usernameValidatorParams, usernameValidatorMessages, usernameValidatorMandatory)

      validation.isFailure should be(true)

      validatorI18n.getFormattedString(locale, "username.validator.username.is.too.long", List(username.length.toString, usernameMaxLength)) should be(validation match {
        case Failure(f) => f.head
        case Success(s) => s
      })

    }

  }

}
