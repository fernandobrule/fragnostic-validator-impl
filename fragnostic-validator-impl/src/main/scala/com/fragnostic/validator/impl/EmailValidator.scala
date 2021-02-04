package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorApi
import scalaz.Scalaz._

import javax.mail.internet.{ AddressException, InternetAddress }
// https://github.com/bbottema/email-rfc2822-validator
import org.hazlewood.connor.bottema.emailaddress.{ EmailAddressCriteria, EmailAddressValidator }

import java.util.EnumSet

trait EmailValidator extends ValidatorApi[String] {

  override def validate(email: String, hasToFormat: Boolean, messages: String*): Validated[String] =
    if (email.trim.isEmpty) {
      messages(0).failureNel // emptyTextMessage
    } else {
      validateByRfc2822Validator(email, messages(0), messages(1)) //emptyTextMessage, errorMessage)
    }

  private def validateByRfc2822Validator(email: String, emptyTextMessage: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorWithAllows(email: String, emptyTextMessage: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, java.util.EnumSet.of(EmailAddressCriteria.ALLOW_DOT_IN_A_TEXT, EmailAddressCriteria.ALLOW_SQUARE_BRACKETS_IN_A_TEXT))) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorRfcCompliant(email: String, emptyTextMessage: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RFC_COMPLIANT)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorRecommended(email: String, emptyTextMessage: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RECOMMENDED)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private val owaspEmailValidatorRegex = """^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"""
  private def validateByOwaspRegex(email: String, emptyTextMessage: String, errorMessage: String): Validated[String] =
    if (email.matches(owaspEmailValidatorRegex)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByInternetAddress(email: String, emptyTextMessage: String, errorMessage: String): Validated[String] =
    try {
      new InternetAddress(email.trim).validate()
      email.trim.successNel
    } catch {
      case e: AddressException => errorMessage.failureNel
    }

}
