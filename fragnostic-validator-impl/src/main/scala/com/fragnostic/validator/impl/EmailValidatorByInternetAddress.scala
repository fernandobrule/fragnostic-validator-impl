package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.Scalaz._

import javax.mail.internet.{ AddressException, InternetAddress }

trait EmailValidatorByInternetAddress {

  def validateByInternetAddress(email: String, errorMessage: String): Validated[String] =
    try {
      new InternetAddress(email.trim).validate()
      email.trim.successNel
    } catch {
      case e: AddressException => errorMessage.failureNel
    }

}
