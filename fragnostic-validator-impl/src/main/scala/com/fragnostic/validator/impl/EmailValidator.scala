package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import scalaz.Scalaz._
import javax.mail.internet.InternetAddress
import javax.mail.internet.AddressException
// https://github.com/bbottema/email-rfc2822-validator
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator
import org.hazlewood.connor.bottema.emailaddress.EmailAddressCriteria
import java.util.EnumSet

trait EmailValidator extends UnderValidation {

  def validateEmail(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (email.trim.isEmpty) {
      emptyTextMessage.failureNel
    } else {
      validateByRfc2822Validator(email, emptyTextMessage, errorMessage)
    }

  private def validateByRfc2822Validator(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (EmailAddressValidator.isValid(email)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorWithAllows(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (EmailAddressValidator.isValid(email, EnumSet.of(EmailAddressCriteria.ALLOW_DOT_IN_A_TEXT, EmailAddressCriteria.ALLOW_SQUARE_BRACKETS_IN_A_TEXT))) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorRfcCompliant(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RFC_COMPLIANT)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorRecommended(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RECOMMENDED)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private val owaspEmailValidatorRegex = """^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"""
  private def validateByOwaspRegex(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (email.matches(owaspEmailValidatorRegex)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByInternetAddress(email: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    try {
      new InternetAddress(email.trim).validate()
      email.trim.successNel
    } catch {
      case e: AddressException => {
        System.err.println(s"validateEmail() - $e")
        errorMessage.failureNel
      }
    }

}
