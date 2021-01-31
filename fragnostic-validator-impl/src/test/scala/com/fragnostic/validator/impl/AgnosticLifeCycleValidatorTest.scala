package com.fragnostic.validator.impl

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class AgnosticLifeCycleValidatorTest extends AnyFunSpec with Matchers with BeforeAndAfterEach {

  protected val mobileValidatorEmptyTextErrorMessage: String = "Você não digitou o número do celular"
  protected val mobileValidatorWithoutCountryCodeErrorMessage: String = "Você não inseriu o código do país"
  protected val mobileValidatorGenericErrorMessage: String = "O número do celular não é válido"

}
