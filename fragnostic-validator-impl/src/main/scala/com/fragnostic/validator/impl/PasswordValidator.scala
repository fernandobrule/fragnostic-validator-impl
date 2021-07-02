package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.{ TypeShortHandler, ValidatorSupport }
import scalaz.Scalaz._

import java.util.Locale
import scala.util.matching.Regex

// TODO esta es una implementación absolutamente mínima
class PasswordValidator extends ValidatorApi[String] with ValidatorSupport with TypeShortHandler {

  private val CONTAINS_AT_LEAST_ONE_UPPERCASE_LETTER: Regex = """.*([A-Z]).*""".r
  private val CONTAINS_AT_LEAST_ONE_LOWERCASE_LETTER: Regex = """.*([a-z]).*""".r
  private val CONTAINS_AT_LEAST_ONE_NUMBER: Regex = """.*(\d).*""".r
  private val CONTAINS_AT_LEAST_ONE_SYMBOL: Regex = """.*([~`!@#$%^&*()-+={\[}]|\\:;"'<,>.?/_]).*""".r

  private def containsAtLeastOneUppercaseLetter(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_UPPERCASE_LETTER(a) => Right(password)
      case _ => Left("password.validator.password.should.contain.at.least.one.uppercase.letter")
    }

  private def containsAtLeastOneLowercaseLetter(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_LOWERCASE_LETTER(a) => Right(password)
      case _ => Left("password.validator.password.should.contain.at.least.one.lowercase.letter")
    }

  private def containsAtLeastOneNumber(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_NUMBER(a) => Right(password)
      case _ => Left("password.validator.password.should.contain.at.least.one.number")
    }

  private def containsAtLeastOneSymbol(password: String): Either[String, String] =
    password match {
      case CONTAINS_AT_LEAST_ONE_SYMBOL(a) => Right(password)
      case _ => Left("password.validator.password.should.contain.at.least.one.symbol")
    }

  override def validate(locale: Locale, domain: String, password: String, params: Map[String, String], messages: List[String], mandatory: Boolean = true): Validated[String] =
    if (password.trim.isEmpty) {
      getErrorMessage(locale, "password.validator.password.is.empty", Nil, validatorI18n, idxTextEmpty, messages).failureNel
    } else {
      handleShort("minLength", domain, params) fold (
        error => error.failureNel,
        minLength => handleShort("maxLength", domain, params) fold (
          error => error.failureNel,
          maxLength =>
            if (password.trim.length < minLength) {
              getErrorMessage(locale, "password.validator.password.is.shorter", List(password.trim.length.toString, minLength.toString), validatorI18n, idxTextShorter, messages).failureNel
            } else if (password.trim.length > maxLength) {
              getErrorMessage(locale, "password.validator.password.is.lengthier", List(password.trim.length.toString, maxLength.toString), validatorI18n, idxTextLengthier, messages).failureNel
            } else {
              containsAtLeastOneUppercaseLetter(password) fold (
                error => getErrorMessage(locale, error, Nil, validatorI18n, idxPasswordMustHaveAtLeastOneUppercaseLetter, messages).failureNel,
                password => containsAtLeastOneLowercaseLetter(password) fold (
                  error => getErrorMessage(locale, error, Nil, validatorI18n, idxPasswordMustHaveAtLeastOneLowercaseLetter, messages).failureNel,
                  password => containsAtLeastOneNumber(password) fold (
                    error => getErrorMessage(locale, error, Nil, validatorI18n, idxPasswordMustHaveAtLeastOneNumber, messages).failureNel,
                    password => containsAtLeastOneSymbol(password) fold (
                      error => getErrorMessage(locale, error, Nil, validatorI18n, idxPasswordMustHaveAtLeastOneSymbol, messages).failureNel,
                      password => password.trim.successNel) //
                  ) //
                ) //
              ) //
            }) //
      ) //
    }

}
