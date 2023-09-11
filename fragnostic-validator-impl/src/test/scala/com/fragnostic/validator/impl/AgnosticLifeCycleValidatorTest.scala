package com.fragnostic.validator.impl

import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.i18n.{ ValidatorI18n, ValidatorMessagesKeys }
import com.fragnostic.validator.support.ValidatorSupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.slf4j.{ Logger, LoggerFactory }

import java.util.Locale

class AgnosticLifeCycleValidatorTest extends AnyFunSpec
  with BeforeAndAfterEach
  with ValidatorSupport
  with ValidatorMessagesKeys {

  val logger: Logger = LoggerFactory.getLogger("AgnosticLifeCycleValidatorTest")

  override def beforeEach(): Unit =
    CakeConfCacheService.confCacheService.delAllKeys fold (
      error => logger.error(s"beforeAll() - $error"),
      message => message //
    )

  def validatorI18n: ResourceI18n = new ValidatorI18n

  val paramsEmpty: Map[String, String] = Map.empty

  val localePtBr: Locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build()

  val mobileValidator = new MobileValidator()

  //
  // Email Validator
  val emailValidator = new EmailValidator()
  val emailValidatorMaxLength = "255"
  val emailValidatorParams: Map[String, String] = Map( //
    CONF_MIN_LENGTH -> "6", //
    CONF_MAX_LENGTH -> emailValidatorMaxLength //
  )
  val msgEmailIsEmpty: String = validatorI18n.getString(localePtBr, MSG_EMAIL_VALIDATOR_EMAIL_IS_EMPTY)
  val msgEmailIsTooShort: String = validatorI18n.getString(localePtBr, MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT)
  val msgEmailIsTooLong: String = validatorI18n.getString(localePtBr, MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG)
  val msgEmailIsNotValid: String = validatorI18n.getString(localePtBr, MSG_EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID)
  val emailValidatorMessages: Map[String, String] = Map(
    MSG_EMAIL_VALIDATOR_EMAIL_IS_EMPTY -> msgEmailIsEmpty,
    MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_SHORT -> msgEmailIsTooShort,
    MSG_EMAIL_VALIDATOR_EMAIL_IS_TOO_LONG -> msgEmailIsTooLong,
    MSG_EMAIL_VALIDATOR_EMAIL_IS_NOT_VALID -> msgEmailIsNotValid //
  )
  val emailValidatorDomain = "Email"

  //
  // CPF Validator
  val cpfValidator = new CpfValidator()
  val msgCpfIsEmpty: String = validatorI18n.getString(localePtBr, MSG_CPF_VALIDATOR_CPF_IS_EMPTY)
  val msgCpfIsTooShort: String = validatorI18n.getString(localePtBr, MSG_CPF_VALIDATOR_CPF_IS_TOO_SHORT)
  val msgCpfIsTooLong: String = validatorI18n.getString(localePtBr, MSG_CPF_VALIDATOR_CPF_IS_TOO_LONG)
  val msgCpfIsNotValid: String = validatorI18n.getString(localePtBr, MSG_CPF_VALIDATOR_CPF_IS_NOT_VALID)
  val cpfValidatorMessages = Map(
    MSG_CPF_VALIDATOR_CPF_IS_EMPTY -> msgCpfIsEmpty,
    MSG_CPF_VALIDATOR_CPF_IS_TOO_SHORT -> msgCpfIsTooShort,
    MSG_CPF_VALIDATOR_CPF_IS_TOO_LONG -> msgCpfIsTooLong,
    MSG_CPF_VALIDATOR_CPF_IS_NOT_VALID -> msgCpfIsNotValid //
  )

  //
  // Date Time Validator
  val dateTimeValidator = new DateTimeValidator()
  val dateValidator = new DateValidator()
  val dateIntervalValidator = new DateIntervalValidator()

  //
  // Validator Messages
  val msgCepIsEmpty: String = validatorI18n.getString(localePtBr, MSG_CEP_VALIDATOR_CEP_IS_EMPTY)
  val msgCepIsNotValid: String = validatorI18n.getString(localePtBr, MSG_CEP_VALIDATOR_CEP_IS_NOT_VALID)

  val msgUrlIsEmpty: String = validatorI18n.getString(localePtBr, MSG_URL_VALIDATOR_URL_IS_EMPTY)
  val msgUrlIsNotValid: String = validatorI18n.getString(localePtBr, MSG_URL_VALIDATOR_URL_IS_NOT_VALID)

  val msgDateTimeIsEmpty: String = validatorI18n.getString(localePtBr, MSG_DATE_TIME_VALIDATOR_DATE_TIME_IS_EMPTY)
  val msgDateTimeIsNotValid: String = validatorI18n.getString(localePtBr, MSG_DATE_TIME_VALIDATOR_DATE_TIME_IS_NOT_VALID)

  val msgDateIsEmpty: String = validatorI18n.getString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_EMPTY)
  def msgDateIsTooLong(domain: String): String = validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domain))
  val msgDateIsNull: String = validatorI18n.getString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NULL)
  def msgDateIsNotValid(domain: String): String = validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domain))
  val msgMobileIsEmpty: String = validatorI18n.getString(localePtBr, MSG_MOBILE_VALIDATOR_MOBILE_IS_EMPTY)
  val msgMobileIsNotValid: String = validatorI18n.getString(localePtBr, MSG_MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID)
  val msgMobileIsLengthier: String = validatorI18n.getString(localePtBr, MSG_MOBILE_VALIDATOR_MOBILE_IS_TOO_LONG)
  val msgMobileWithoutCountryCode: String = validatorI18n.getString(localePtBr, MSG_MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE)
  val mobileValidatorMessages: Map[String, String] = Map(
    MSG_MOBILE_VALIDATOR_MOBILE_IS_EMPTY -> msgMobileIsEmpty,
    MSG_MOBILE_VALIDATOR_MOBILE_IS_NOT_VALID -> msgMobileIsNotValid,
    MSG_MOBILE_VALIDATOR_MOBILE_WITHOUT_COUNTRY_CODE -> msgMobileWithoutCountryCode //
  )

  //
  // Mobile Validator
  val mobileValidatorParamMaxLength: String = "22"
  val mobileValidatorParams: Map[String, String] = Map(
    CONF_MIN_LENGTH -> "11",
    CONF_MAX_LENGTH -> mobileValidatorParamMaxLength,
    CONF_HAS_TO_FORMAT -> "true",
    CONF_VALIDATE_COUNTRY_CODE -> "true",
    CONF_COUNTRY_CODES_WHITE_LIST -> "54;55;56;598" //
  )

  def messagesDateIntervalValidator(domainStart: String, domainEnd: String): Map[String, String] = Map(
    s"${MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID}_$domainStart" -> validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domainStart)),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_NULL}_$domainStart" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_NULL),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY}_$domainStart" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT}_$domainStart" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG}_$domainStart" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG),
    s"${MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID}_$domainEnd" -> validatorI18n.getFormattedString(localePtBr, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, List(domainEnd)),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_NULL}_$domainEnd" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_NULL),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY}_$domainEnd" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_EMPTY),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT}_$domainEnd" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_TOO_SHORT),
    s"${MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG}_$domainEnd" -> validatorI18n.getString(localePtBr, MSG_TEXT_VALIDATOR_TEXT_IS_TOO_LONG),
    MSG_DATE_INTERVAL_VALIDATOR_START_DATE_IS_AFTER_END_DATE -> validatorI18n.getString(localePtBr, MSG_DATE_INTERVAL_VALIDATOR_START_DATE_IS_AFTER_END_DATE) //
  )

  val paramsDateIntervalValidator: Map[String, String] = Map(
    CONF_DATE_FORMAT -> "dd-MM-yyyy",
    CONF_MIN_LENGTH -> "10",
    CONF_MAX_LENGTH -> "10" //
  )

}
