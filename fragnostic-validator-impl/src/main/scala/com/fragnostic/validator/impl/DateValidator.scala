package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.i18n.ValidatorMessagesKeys
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.text.SimpleDateFormat
import java.util.Locale
import scala.util.Try

class DateValidator extends ValidatorApi[String] with ValidatorSupport with ValidatorMessagesKeys {

  //private[this] val logger: Logger = LoggerFactory.getLogger("TextValidator")

  private val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"
  private def textValidator = new TextValidator

  override def validate(locale: Locale, domain: String, date: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] = {
    //logger.info(s"validate() - enter, locale[$locale], domain[$domain], date[$date]")
    textValidator.validate(locale, domain, date, params, messages, mandatory) fold (
      error => error.head.failureNel,
      date =>
        if (date.isEmpty) {
          "".successNel
        } else {
          val dateFormat = params.getOrElse(CONF_DATE_FORMAT, DEFAULT_DATE_FORMAT)
          val simpl = new SimpleDateFormat(dateFormat)
          Try(simpl.parse(date)) fold (
            error => getFailureNel(locale, domain, MSG_DATE_VALIDATOR_DATE_IS_NOT_VALID, messages),
            jdate => s"$date".successNel)
        } //
    )
  }

}

