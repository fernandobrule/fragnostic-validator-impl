package com.fragnostic.validator.impl

import com.fragnostic.validator.support.ValidatorSupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.util.Locale

class AgnosticLifeCycleValidatorTest extends AnyFunSpec with Matchers with BeforeAndAfterEach with ValidatorSupport {

  val params: Map[String, String] = Map[String, String]()

  val locale: Locale = new Locale.Builder().setRegion("BR").setLanguage("pt").build()

  val mobileValidator = new MobileValidator()

  //
  // Email Validator
  val emailValidator = new EmailValidator()
  val emailValidatorMaxLength = "255"
  val emailValidatorParams: Map[String, String] = Map("maxLength" -> emailValidatorMaxLength)
  val msgEmailIsEmpty: String = validatorI18n.getString(locale, "email.validator.email.is.empty")
  val msgEmailIsNotValid: String = validatorI18n.getString(locale, "email.validator.email.is.not.valid")
  val emailValidatorMessages: List[String] = List(msgEmailIsEmpty, msgEmailIsNotValid)
  val emailValidatorDomain = "Email"

  //
  // CPF Validator
  val cpfValidator = new CpfValidator()

  //
  // Date Time Validator
  val dateTimeValidator = new DateTimeValidator()

  //
  // Validator Messages
  val msgCepIsEmpty: String = validatorI18n.getString(locale, "cep.validator.cep.is.empty")
  val msgCepIsNotValid: String = validatorI18n.getString(locale, "cep.validator.cep.is.not.valid")

  val msgCpfIsEmpty: String = validatorI18n.getString(locale, "cpf.validator.cpf.is.empty")
  val msgCpfIsNotValid: String = validatorI18n.getString(locale, "cpf.validator.cpf.is.not.valid")

  val msgDateTimeIsEmpty: String = validatorI18n.getString(locale, "date.time.validator.date.time.is.empty")
  val msgDateTimeIsNotValid: String = validatorI18n.getString(locale, "date.time.validator.date.time.is.not.valid")

  val msgMobileIsEmpty: String = validatorI18n.getString(locale, "mobile.validator.mobile.is.empty")
  val msgMobileIsNotValid: String = validatorI18n.getString(locale, "mobile.validator.mobile.is.not.valid")
  val msgMobileIsLengthier: String = validatorI18n.getString(locale, "mobile.validator.mobile.is.lengthier")
  val msgMobileWithoutCountryCode: String = validatorI18n.getString(locale, "mobile.validator.mobile.without.country.code")
  val mobileValidatorMessages: List[String] = List(
    msgMobileIsEmpty,
    msgMobileIsNotValid,
    msgMobileIsLengthier,
    msgMobileWithoutCountryCode //
  )

  val msgTextIsEmpty: String = validatorI18n.getString(locale, "text.max.length.validator.text.is.empty")
  val msgTextIsLengthier: String = validatorI18n.getString(locale, "text.max.length.validator.text.is.lengthier")

}
