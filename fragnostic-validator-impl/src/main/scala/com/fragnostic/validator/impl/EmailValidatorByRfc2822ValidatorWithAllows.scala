package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import org.hazlewood.connor.bottema.emailaddress.{ EmailAddressCriteria, EmailAddressValidator }
import scalaz.Scalaz._

trait EmailValidatorByRfc2822ValidatorWithAllows {

  def validateByRfc2822ValidatorWithAllows(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, java.util.EnumSet.of(EmailAddressCriteria.ALLOW_DOT_IN_A_TEXT, EmailAddressCriteria.ALLOW_SQUARE_BRACKETS_IN_A_TEXT))) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

}
