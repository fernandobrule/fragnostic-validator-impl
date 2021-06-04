package com.fragnostic.validator.impl

import com.fragnostic.validator.api.{ Validated, ValidatorApi }
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale
import javax.mail.internet.{ AddressException, InternetAddress }
// https://github.com/bbottema/email-rfc2822-validator
import org.hazlewood.connor.bottema.emailaddress.{ EmailAddressCriteria, EmailAddressValidator }

class EmailValidator extends ValidatorApi[String] with ValidatorSupport {

  private def textMaxLengthValidator = new TextMaxLengthValidator

  override def validate(email: String, locale: Locale, params: Map[String, String], messages: List[String]): Validated[String] = {
    if (email.trim.isEmpty) {
      getErrorMessage(locale, "email.validator.email.is.empty", Nil, validatorI18n, 0, messages).failureNel
    } else {

      (textMaxLengthValidator.validate(email, locale, params, messages)
        |@| validateByRfc2822Validator(email, getErrorMessage(locale, "email.validator.email.is.not.valid", Nil, validatorI18n, 1, messages)) //
      ) {
          (dummy, email) => email
        }

    }
  }

  private def validateByRfc2822Validator(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorWithAllows(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, java.util.EnumSet.of(EmailAddressCriteria.ALLOW_DOT_IN_A_TEXT, EmailAddressCriteria.ALLOW_SQUARE_BRACKETS_IN_A_TEXT))) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorRfcCompliant(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RFC_COMPLIANT)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByRfc2822ValidatorRecommended(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RECOMMENDED)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private val owaspEmailValidatorRegex = """^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"""

  private def validateByOwaspRegex(email: String, errorMessage: String): Validated[String] =
    if (email.matches(owaspEmailValidatorRegex)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

  private def validateByInternetAddress(email: String, errorMessage: String): Validated[String] =
    try {
      new InternetAddress(email.trim).validate()
      email.trim.successNel
    } catch {
      case e: AddressException => errorMessage.failureNel
    }

}
