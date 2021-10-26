package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale
import scala.util.matching.Regex

// TODO esta es una implementación absolutamente mínima
class PasswordValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler with ValidatorMessagesKeys {

  private val CONTAINS_AT_LEAST_ONE_UPPERCASE_LETTER: Regex = """.*([A-Z]).*""".r
  private val CONTAINS_AT_LEAST_ONE_LOWERCASE_LETTER: Regex = """.*([a-z]).*""".r
  private val CONTAINS_AT_LEAST_ONE_NUMBER: Regex = """.*(\d).*""".r
  // https://owasp.org/www-community/password-special-characters
  private val CONTAINS_AT_LEAST_ONE_SYMBOL: Regex = """.*([ !"#$%&'()*+,-./:;<=>?@\[\\\]^_`{|}~]).*""".r

  private def containsAtLeastOneUppercaseLetter(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_UPPERCASE_LETTER(a) => Right(password)
      case _ => Left(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER)
    }

  private def containsAtLeastOneLowercaseLetter(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_LOWERCASE_LETTER(a) => Right(password)
      case _ => Left(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER)
    }

  private def containsAtLeastOneNumber(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_NUMBER(a) => Right(password)
      case _ => Left(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER)
    }

  private def containsAtLeastOneSymbol(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_SYMBOL(a) => Right(password)
      case _ => Left(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL)
    }

  override def validate(locale: Locale, domain: String, password: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (password.trim.isEmpty) {
      messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_IS_EMPTY, s"message___${PASSWORD_VALIDATOR_PASSWORD_IS_EMPTY}___is.not.available").failureNel
    } else {
      handleShort("minLength", domain, params) fold (
        error => error.failureNel,
        minLength => handleShort("maxLength", domain, params) fold (
          error => error.failureNel,
          maxLength =>
            if (password.trim.length < minLength) {
              messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_IS_TOO_SHORT, s"message___${PASSWORD_VALIDATOR_PASSWORD_IS_TOO_SHORT}___is.not.available").failureNel
            } else if (password.trim.length > maxLength) {
              messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_IS_TOO_LONG, s"message___${PASSWORD_VALIDATOR_PASSWORD_IS_TOO_LONG}___is.not.available").failureNel
            } else {
              containsAtLeastOneUppercaseLetter(password) fold (
                error => messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER, s"message___${PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPERCASE_LETTER}___is.not.available").failureNel,
                password => containsAtLeastOneLowercaseLetter(password) fold (
                  error => messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER, s"message___${PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWERCASE_LETTER}___is.not.available").failureNel,
                  password => containsAtLeastOneNumber(password) fold (
                    error => messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER, s"message___${PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER}___is.not.available").failureNel,
                    password => containsAtLeastOneSymbol(password) fold (
                      error => messages.getOrElse(PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL, s"message___${PASSWORD_VALIDATOR_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SYMBOL}___is.not.available").failureNel,
                      password => password.trim.successNel) //
                  ) //
                ) //
              ) //
            }) //
      ) //
    }

}
