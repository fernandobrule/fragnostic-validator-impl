package com.fragnostic.validator.impl;

import com.fragnostic.validator.api._
import scalaz.{ Failure, NonEmptyList, Success }

class PasswordValidatorTest extends AgnosticLifeCycleValidatorTest {

  def passwordValidator = new PasswordValidator()

  val passwordValidatorDomain = "Password"
  val passwordValidatorMaxLength = "64"
  val passwordValidatorParams: Map[String, String] = Map(
    "minLength" -> "8",
    "maxLength" -> passwordValidatorMaxLength //
  )

  val msgPasswordIsEmpty: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_IS_EMPTY)
  val msgPasswordIsNotValid: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_IS_NOT_VALID)
  val msgPasswordIsTooShort: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_IS_TOO_SHORT)
  val msgPasswordMustToHaveAtLeastOneLowercaseLetter: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER)
  val msgPasswordMustToHaveAtLeastOneNumber: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER)
  val msgPasswordMustToHaveAtLeastOneSymbol: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL)
  val msgPasswordMustToHaveAtLeastOneUppercaseLetter: String = validatorI18n.getString(locale, PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER)
  def msgPasswordIsTooLong(passwordLength: String, passwordValidatorMaxLength: String): String = validatorI18n.getFormattedString(locale, PASSWORD_VALIDATOR_PASSWORD_IS_TOO_LONG, List(passwordLength, passwordValidatorMaxLength))

  def messagesPasswordValidator(passwordLength: String, passwordValidatorMaxLength: String) = Map(
    PASSWORD_VALIDATOR_PASSWORD_IS_EMPTY -> msgPasswordIsEmpty,
    PASSWORD_VALIDATOR_PASSWORD_IS_NOT_VALID -> msgPasswordIsNotValid,
    PASSWORD_VALIDATOR_PASSWORD_IS_TOO_LONG -> msgPasswordIsTooLong(passwordLength, passwordValidatorMaxLength),
    PASSWORD_VALIDATOR_PASSWORD_IS_TOO_SHORT -> msgPasswordIsTooShort,
    PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER -> msgPasswordMustToHaveAtLeastOneUppercaseLetter,
    PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER -> msgPasswordMustToHaveAtLeastOneLowercaseLetter,
    PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER -> msgPasswordMustToHaveAtLeastOneNumber,
    PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL -> msgPasswordMustToHaveAtLeastOneSymbol //
  )

  describe("Password Validator Test") {

    //
    // Passwords should contain four character types:
    // - Uppercase letters: A-Z
    // - Lowercase letters: a-z
    // - Numbers: 0-9
    // - Symbols: ~`!@#$%^&*()_-+={[}]|\:;"'<,>.?/
    it("Can Validate Valid Password") {

      val password = "8f789sAd#7f89sd"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isSuccess should be(true)

      validation.toList.head should be(password)

    }

    it("Can Validate Password Without At Least One Uppercase Letter") {

      val password = "sdfddfssdfsdfsdfsdf"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordMustToHaveAtLeastOneUppercaseLetter)

    }

    it("Can Validate Password Without At Least One Lowercase Letter") {

      val password = "324A8972389"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordMustToHaveAtLeastOneLowercaseLetter)

    }

    it("Can Validate Password Without At Least One Number") {

      val password = "sdfddfssdfsdfsdfABsdf"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordMustToHaveAtLeastOneNumber)

    }

    it("Can Validate Password Without At Least One Symbol") {

      val password = "sdfddfssA6dfsdfsdfsdf"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordMustToHaveAtLeastOneSymbol)

    }

    it("Can Validate Empty Password") {

      val password = "  "
      val passwordLength: String = "0"
      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(passwordLength, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordIsEmpty)

    }

    it("Can Validate Lengthier Password") {

      val password = "sdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsa@sdfasdfas.com"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordIsTooLong(password.length.toString, passwordValidatorMaxLength))

    }

    it("Can Validate Shorter Password") {

      val password = "7s8df6"

      val validation: Validated[String] = passwordValidator.validate(locale, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgPasswordIsTooShort)

    }

  }

}