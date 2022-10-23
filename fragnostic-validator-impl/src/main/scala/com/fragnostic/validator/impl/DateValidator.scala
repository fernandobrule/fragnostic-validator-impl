package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.text.SimpleDateFormat
import java.util.Locale
import scala.util.Try

class DateValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  private val defaultDateFormat = "dd/MM/yyyy"
  private def textValidator = new TextValidator

  override def validate(locale: Locale, domain: String, date: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {

    //logger.info(s"validate() - enter")

    //messages.foreach(kv => logger.info(s"validate() - messages tuple: ${kv}")) // deletemeplz

    textValidator.validate(locale, domain, date, params, messages, mandatory) fold (
      error => error.head.failureNel,
      date =>
        if (date.isEmpty) {
          "".successNel
        } else {
          val dateFormat = params.getOrElse(CONF_DATE_FORMAT, defaultDateFormat)
          val simpl = new SimpleDateFormat(dateFormat)
          Try(simpl.parse(date)) fold (
            error => {
              //logger.info(s"validate() - $error, $MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID") // deletemeplz
              //messages.foreach(kv => logger.info(s"validate() - ${kv}")) // deletemeplz
              getFailureNel(locale, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, messages)
            },
            jdate => s"$date".successNel)
        } //
    )
  }

}

