package com.fragnostic.validator.impl;

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class PasswordValidatorTest extends AgnosticLifeCycleValidatorTest {

  def passwordValidator = new PasswordValidator()

  val passwordValidatorDomain = "Password"
  val passwordValidatorMaxLength = "64"
  val passwordValidatorParams: Map[String, String] = Map(
    "minLength" -> "8",
    "maxLength" -> passwordValidatorMaxLength //
  )

  val msgPasswordIsEmpty: String = validatorI18n.getString(locale, "password.validator.password.is.empty")
  val msgPasswordIsNotValid: String = validatorI18n.getString(locale, "password.validator.password.is.not.valid")
  val msgPasswordIsShorter: String = validatorI18n.getString(locale, "password.validator.password.is.too.short")
  val msgPasswordMustToHaveAtLeastOneLowercaseLetter: String = validatorI18n.getString(locale, "password.validator.password.should.contain.at.least.one.lowercase.letter")
  val msgPasswordMustToHaveAtLeastOneNumber: String = validatorI18n.getString(locale, "password.validator.password.should.contain.at.least.one.number")
  val msgPasswordMustToHaveAtLeastOneSymbol: String = validatorI18n.getString(locale, "password.validator.password.should.contain.at.least.one.symbol")
  val msgPasswordMustToHaveAtLeastOneUppercaseLetter: String = validatorI18n.getString(locale, "password.validator.password.should.contain.at.least.one.uppercase.letter")
  def msgPasswordIsLengthier(passwordLength: String, passwordValidatorMaxLength: String): String = validatorI18n.getFormattedString(locale, "password.validator.password.is.too.long", List(passwordLength, passwordValidatorMaxLength))

  def messagesPasswordValidator(passwordLength: String, passwordValidatorMaxLength: String) = List(
    msgPasswordIsEmpty,
    msgPasswordIsNotValid,
    msgPasswordIsLengthier(passwordLength, passwordValidatorMaxLength),
    "N.A.",
    msgPasswordIsShorter,
    msgPasswordMustToHaveAtLeastOneUppercaseLetter,
    msgPasswordMustToHaveAtLeastOneLowercaseLetter,
    msgPasswordMustToHaveAtLeastOneNumber,
    msgPasswordMustToHaveAtLeastOneSymbol //
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
      }) should be(msgPasswordIsLengthier(password.length.toString, passwordValidatorMaxLength))

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
      }) should be(msgPasswordIsShorter)

    }

  }

}