package com.fragnostic.validator.impl
import com.fragnostic.validator.api.Validated
import org.hazlewood.connor.bottema.emailaddress.{ EmailAddressCriteria, EmailAddressValidator }
import scalaz.Scalaz._

trait EmailValidatorByRfc2822ValidatorRecommended {

  def validateByRfc2822ValidatorRecommended(email: String, errorMessage: String): Validated[String] =
    if (EmailAddressValidator.isValid(email, EmailAddressCriteria.RECOMMENDED)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

}
