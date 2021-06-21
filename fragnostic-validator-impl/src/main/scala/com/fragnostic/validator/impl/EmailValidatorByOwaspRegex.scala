package com.fragnostic.validator.impl
import com.fragnostic.validator.api.Validated
import scalaz.Scalaz._

trait EmailValidatorByOwaspRegex {

  private val owaspEmailValidatorRegex = """^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"""

  def validateByOwaspRegex(email: String, errorMessage: String): Validated[String] =
    if (email.matches(owaspEmailValidatorRegex)) {
      email.trim.successNel
    } else {
      errorMessage.failureNel
    }

}
