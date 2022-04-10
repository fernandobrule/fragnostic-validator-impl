package com.fragnostic.validator.impl

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.i18n.{ ValidatorI18n, ValidatorMessagesKeys }
import com.fragnostic.validator.support.ValidatorSupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec

import java.util.Locale

class AgnosticLifeCycleValidatorTest extends AnyFunSpec with BeforeAndAfterEach with ValidatorSupport with ValidatorMessagesKeys {

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
  val msgEmailIsEmpty: String = validatorI18n.getString(locale, EMAIL_VALIDATOR_EMAIL_IS_EMPTY)
  val msgEmailIsTooShort: String = validatorI18n.getString(locale, EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT)
  val msgEmailIsTooLong: String = validatorI18n.getString(locale, EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG)
  val msgEmailIsNotValid: String = validatorI18n.getString(locale, EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID)
  val emailValidatorMessages: Map[String, String] = Map(
    EMAIL_VALIDATOR_EMAIL_IS_EMPTY -> msgEmailIsEmpty,
    EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT -> msgEmailIsTooShort,
    EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG -> msgEmailIsTooLong,
    EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID -> msgEmailIsNotValid //
  )
  val emailValidatorDomain = "Email"

  //
  // CPF Validator
  val cpfValidator = new CpfValidator()
  val msgCpfIsEmpty: String = validatorI18n.getString(locale, CPF_VALIDATOR_CPF_IS_EMPTY)
  val msgCpfIsTooShort: String = validatorI18n.getString(locale, CPF_VALIDATOR_CPF_IS_TOO_SHORT)
  val msgCpfIsTooLong: String = validatorI18n.getString(locale, CPF_VALIDATOR_CPF_IS_TOO_LONG)
  val msgCpfIsNotValid: String = validatorI18n.getString(locale, CPF_VALIDATOR_CPF_IS_NOT_VALID)
  val cpfValidatorMessages = Map(
    CPF_VALIDATOR_CPF_IS_EMPTY -> msgCpfIsEmpty,
    CPF_VALIDATOR_CPF_IS_TOO_SHORT -> msgCpfIsTooShort,
    CPF_VALIDATOR_CPF_IS_TOO_LONG -> msgCpfIsTooLong,
    CPF_VALIDATOR_CPF_IS_NOT_VALID -> msgCpfIsNotValid //
  )

  //
  // Date Time Validator
  val dateTimeValidator = new DateTimeValidator()
  val dateValidator = new DateValidator()

  //
  // Validator Messages
  val msgCepIsEmpty: String = validatorI18n.getString(locale, CEP_VALIDATOR_CEP_IS_EMPTY)
  val msgCepIsNotValid: String = validatorI18n.getString(locale, CEP_VALIDATOR_CEP_IS_NOT_VALID)

  val msgUrlIsEmpty: String = validatorI18n.getString(locale, URL_VALIDATOR_URL_IS_EMPTY)
  val msgUrlIsNotValid: String = validatorI18n.getString(locale, URL_VALIDATOR_URL_IS_NOT_VALID)

  val msgDateTimeIsEmpty: String = validatorI18n.getString(locale, DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY)
  val msgDateTimeIsNotValid: String = validatorI18n.getString(locale, DATE_VALIDATOR_DATE_IS_NOT_VALID)

  val msgDateIsEmpty: String = validatorI18n.getString(locale, DATE_VALIDATOR_DATE_IS_EMPTY)
  val msgDateIsNotValid: String = validatorI18n.getString(locale, DATE_VALIDATOR_DATE_IS_NOT_VALID)

  val msgMobileIsEmpty: String = validatorI18n.getString(locale, MOBILE_VALIDATOR_MOBILE_IS_EMPTY)
  val msgMobileIsNotValid: String = validatorI18n.getString(locale, MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID)
  val msgMobileIsLengthier: String = validatorI18n.getString(locale, MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG)
  val msgMobileWithoutCountryCode: String = validatorI18n.getString(locale, MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE)
  val mobileValidatorMessages: Map[String, String] = Map(
    MOBILE_VALIDATOR_MOBILE_IS_EMPTY -> msgMobileIsEmpty,
    MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID -> msgMobileIsNotValid,
    MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE -> msgMobileWithoutCountryCode //
  )

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
