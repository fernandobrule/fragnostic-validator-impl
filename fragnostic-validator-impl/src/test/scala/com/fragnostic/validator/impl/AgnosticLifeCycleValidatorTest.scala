package com.fragnostic.validator.impl

import com.fragnostic.validator.i18n.ValidatorI18n
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.util.Locale

class AgnosticLifeCycleValidatorTest extends AnyFunSpec with Matchers with BeforeAndAfterEach {

  val hasToFormat = true

  val locale: Locale = new Locale.Builder().setRegion("BR").setLanguage("pt").build()

  val mobileValidator = new MobileValidator()
  val emailValidator = new EmailValidator()
  val cpfValidator = new CpfValidator()

  val i18n = new ValidatorI18n()

  val msgMobileEmpty: String = i18n.getString(locale, "mobile.validator.mobile.empty")
  val msgMobileNotValid: String = i18n.getString(locale, "mobile.validator.mobile.not.valid")
  val msgMobileWhitoutCountryCode: String = i18n.getString(locale, "mobile.validator.mobile.whitout.country.code")

  val msgCpfEmpty: String = i18n.getString(locale, "cpf.validator.cpf.empty")
  val msgCpfNotValid: String = i18n.getString(locale, "cpf.validator.cpf.not.valid")

  val msgEmailEmpty: String = i18n.getString(locale, "email.validator.email.empty")
  val msgEmailNotValid: String = i18n.getString(locale, "email.validator.email.not.valid")

}
