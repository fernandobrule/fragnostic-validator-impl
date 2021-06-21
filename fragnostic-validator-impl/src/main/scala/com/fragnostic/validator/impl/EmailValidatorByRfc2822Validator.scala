package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator
import scalaz.Scalaz._

// https://github.com/bbottema/email-rfc2822-validator
trait EmailValidatorByRfc2822Validator {

  def validateByRfc2822Validator(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

}
