package com.fragnostic.validator.support

trait ValidatorSupport {

  def argsAreValid(numberExpected: Int, messages: String*): Boolean =
    numberExpected == messages.length

}
