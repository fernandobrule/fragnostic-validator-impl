package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api.VALIDATOR_TEXT_TOO_LONG
import com.fragnostic.validator.i18n.ValidatorI18n
import com.fragnostic.validator.support.ValidatorSupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.util.Locale

class AgnosticLifeCycleValidatorTest extends AnyFunSpec with Matchers with BeforeAndAfterEach with ValidatorSupport {

  def validatorI18n: ResourceI18n = new ValidatorI18n

  val paramsEmpty: Map[String, String] = Map.empty

  val locale: Locale = new Locale.Builder().setRegion("BR").setLanguage("pt").build()

  val mobileValidator = new MobileValidator()

  //
  // Email Validator
  val emailValidator = new EmailValidator()
  val emailValidatorMaxLength = "255"
  val emailValidatorParams: Map[String, String] = Map( //
    "minLength" -> "6", //
    "maxLength" -> emailValidatorMaxLength //
  )
  val msgEmailIsEmpty: String = validatorI18n.getString(locale, "email.validator.email.is.empty")
  val msgEmailIsNotValid: String = validatorI18n.getString(locale, "email.validator.email.is.not.valid")
  val emailValidatorMessages: Map[String, String] = Map(
    "text.boundaries.validator.text.is.empty" -> msgEmailIsEmpty,
    "email.validator.email.is.not.valid" -> msgEmailIsNotValid)
  val emailValidatorDomain = "Email"

  //
  // CPF Validator
  val cpfValidator = new CpfValidator()

  //
  // Date Time Validator
  val dateTimeValidator = new DateTimeValidator()
  val dateValidator = new DateValidator()

  //
  // Validator Messages
  val msgCepIsEmpty: String = validatorI18n.getString(locale, "cep.validator.cep.is.empty")
  val msgCepIsNotValid: String = validatorI18n.getString(locale, "cep.validator.cep.is.not.valid")

  val msgCpfIsEmpty: String = validatorI18n.getString(locale, "cpf.validator.cpf.is.empty")
  val msgCpfIsNotValid: String = validatorI18n.getString(locale, "cpf.validator.cpf.is.not.valid")

  val msgDateTimeIsEmpty: String = validatorI18n.getString(locale, "date.time.validator.date.time.is.empty")
  val msgDateTimeIsNotValid: String = validatorI18n.getString(locale, "date.time.validator.date.time.is.not.valid")

  val msgDateIsEmpty: String = validatorI18n.getString(locale, "date.validator.date.is.empty")
  val msgDateIsNotValid: String = validatorI18n.getString(locale, "date.validator.date.is.not.valid")

  val msgMobileIsEmpty: String = validatorI18n.getString(locale, "mobile.validator.mobile.is.empty")
  val msgMobileIsNotValid: String = validatorI18n.getString(locale, "mobile.validator.mobile.is.not.valid")
  val msgMobileIsLengthier: String = validatorI18n.getString(locale, "mobile.validator.mobile.is.too.long")
  val msgMobileWithoutCountryCode: String = validatorI18n.getString(locale, "mobile.validator.mobile.without.country.code")
  val mobileValidatorMessages: Map[String, String] = Map(
    "mobile.validator.mobile.is.empty" -> msgMobileIsEmpty,
    "mobile.validator.mobile.is.not.valid" -> msgMobileIsNotValid,
    "mobile.validator.mobile.without.country.code" -> msgMobileWithoutCountryCode //
  )

  val msgTextIsEmpty: String = validatorI18n.getString(locale, "text.max.length.validator.text.is.empty")
  val msgTextIsLengthier: String = validatorI18n.getString(locale, "text.max.length.validator.text.is.too.long")

  //
  // Mobile Validator
  val mobileValidatorParamMaxLength: String = "22"
  val mobileValidatorParams: Map[String, String] = Map(
    "minLength" -> "11",
    "maxLength" -> mobileValidatorParamMaxLength,
    "hasToFormat" -> "true",
    "validateCountryCode" -> "true",
    "countryCodesWhiteList" -> "54;55;56;598" //
  )

}
