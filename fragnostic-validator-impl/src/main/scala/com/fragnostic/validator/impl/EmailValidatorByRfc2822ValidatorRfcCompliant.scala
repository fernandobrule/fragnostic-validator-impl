package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import org.hazlewood.connor.bottema.emailaddress.{ EmailAddressCriteria, EmailAddressValidator }
import scalaz.Scalaz._

trait EmailValidatorByRfc2822ValidatorRfcCompliant {

  private def validateByRfc2822ValidatorRfcCompliant(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RFC_COMPLIANT)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

}
