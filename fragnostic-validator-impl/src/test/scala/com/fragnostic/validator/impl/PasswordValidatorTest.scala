package com.fragnostic.validator.impl;

import com.fragnostic.validator.api._
import scalaz.{ Failure, NonEmptyList, Success }

class PasswordValidatorTest extends AgnosticLifeCycleValidatorTest {

  def passwordValidator = new PasswordValidator()

  val passwordValidatorDomain = "Password"
  val passwordValidatorMaxLength = "64"
  val passwordValidatorParams: Map[String, String] = Map(
    CONF_MIN_LENGTH -> "8",
    CONF_MAX_LENGTH -> passwordValidatorMaxLength //
  )

  val msgPasswordIsEmpty: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_IS_EMPTY)
  val msgPasswordIsNotValid: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_IS_NOT_VALID)
  val msgPasswordIsTooShort: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_IS_TOO_SHORT)
  val msgPasswordMustToHaveAtLeastOneLowercaseLetter: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER)
  val msgPasswordMustToHaveAtLeastOneNumber: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER)
  val msgPasswordMustToHaveAtLeastOneSymbol: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL)
  val msgPasswordMustToHaveAtLeastOneUppercaseLetter: String = validatorI18n.getString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER)
  def msgPasswordIsTooLong(passwordLength: String, passwordValidatorMaxLength: String): String = validatorI18n.getFormattedString(localePtBr, MSG_PASSWORD_VALIDATOR_PASSWORD_IS_TOO_LONG, List(passwordLength, passwordValidatorMaxLength))

  def messagesPasswordValidator(passwordLength: String, passwordValidatorMaxLength: String) = Map(
    MSG_PASSWORD_VALIDATOR_PASSWORD_IS_EMPTY -> msgPasswordIsEmpty,
    MSG_PASSWORD_VALIDATOR_PASSWORD_IS_NOT_VALID -> msgPasswordIsNotValid,
    MSG_PASSWORD_VALIDATOR_PASSWORD_IS_TOO_LONG -> msgPasswordIsTooLong(passwordLength, passwordValidatorMaxLength),
    MSG_PASSWORD_VALIDATOR_PASSWORD_IS_TOO_SHORT -> msgPasswordIsTooShort,
    MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER -> msgPasswordMustToHaveAtLeastOneUppercaseLetter,
    MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER -> msgPasswordMustToHaveAtLeastOneLowercaseLetter,
    MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER -> msgPasswordMustToHaveAtLeastOneNumber,
    MSG_PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL -> msgPasswordMustToHaveAtLeastOneSymbol //
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

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isSuccess)(true)

      assertResult(validation.toList.head)(password)

    }

    it("Can Validate Valid Password in the border") {

      val password = "b%1Kdlrjfunhkujknpqkecrqywafqlsydpdjcpjlcsqnuybdqkqoqvqgbjyomhnb"
      assertResult(64)(password.length)

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isSuccess)(true)

      assertResult(validation.toList.head)(password)

    }

    it("Can Validate Password Without At Least One Uppercase Letter") {

      val password = "sdfddfssdfsdfsdfsdf"

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordMustToHaveAtLeastOneUppercaseLetter)

    }

    it("Can Validate Password Without At Least One Lowercase Letter") {

      val password = "324A8972389"

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordMustToHaveAtLeastOneLowercaseLetter)

    }

    it("Can Validate Password Without At Least One Number") {

      val password = "sdfddfssdfsdfsdfABsdf"

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordMustToHaveAtLeastOneNumber)

    }

    it("Can Validate Password Without At Least One Symbol") {

      val password = "sdfddfssA6dfsdfsdfsdf"

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordMustToHaveAtLeastOneSymbol)

    }

    it("Can Validate Empty Password") {

      val password = "  "
      val passwordLength: String = "0"
      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(passwordLength, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordIsEmpty)

    }

    it("Can Validate Lengthier Password") {

      val password = "sdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsasdfasdfasdfasdfsa@sdfasdfas.com"

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordIsTooLong(password.length.toString, passwordValidatorMaxLength))

    }

    it("Can Validate Shorter Password") {

      val password = "7s8df6"

      val validation: Validated[String] = passwordValidator.validate(localePtBr, passwordValidatorDomain, password, passwordValidatorParams, messagesPasswordValidator(password.length.toString, passwordValidatorMaxLength))

      assertResult(validation.isFailure)(true)

      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgPasswordIsTooShort)

    }

  }

}