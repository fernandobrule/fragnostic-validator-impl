package com.fragnostic.validator.support

import com.fragnostic.validator.api.Validated
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

import java.util.Locale

trait ValidatorSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ValidatorSupport")

  val CONF_DATE_FORMAT: String = "CONF_DATE_FORMAT"
  val CONF_MAX_LENGTH: String = "CONF_MAX_LENGTH"
  val CONF_MIN_LENGTH: String = "CONF_MIN_LENGTH"
  val CONF_MIN_VALUE: String = "CONF_MIN_VALUE"
  val CONF_MAX_VALUE: String = "CONF_MAX_VALUE"
  val CONF_HAS_TO_FORMAT: String = "CONF_HAS_TO_FORMAT"
  val CONF_VALIDATE_COUNTRY_CODE: String = "CONF_VALIDATE_COUNTRY_CODE"
  val CONF_COUNTRY_CODES_WHITE_LIST: String = "CONF_COUNTRY_CODES_WHITE_LIST"

  def argsAreValid(numberExpected: Int, messages: Map[String, String]): Boolean = numberExpected == messages.size

  private def lookForKey(params: Map[String, String], head: (String, String), newMap: Map[String, String] = Map.empty): Map[String, String] = {
    val key = head._1
    val domain = head._2
    if (params.contains(s"$key$domain")) {
      newMap + (key -> params(s"$key$domain"))
    } else if (params.contains(key)) {
      newMap + (key -> params(key))
    } else {
      newMap
    }
  }

  def lookForKeys(params: Map[String, String], keyDomain: List[(String, String)], newMap: Map[String, String] = Map.empty): Map[String, String] =
    keyDomain match {
      case Nil => newMap
      case head :: Nil => lookForKey(params, head, newMap)
      case head :: tail =>
        val key = head._1
        val domain = head._2
        lookForKeys(params, tail, newMap) + {
          if (params.contains(s"$key$domain")) {
            key -> params(s"$key$domain")
          } else if (params.contains(key)) {
            key -> params(key)
          } else {
            ("" -> "")
          }
        }
    }

  def lookForKey(params: Map[String, String], key: String, domain: String): Map[String, String] =
    if (params.contains(s"$key$domain")) {
      Map(key -> params(s"$key$domain"))
    } else if (params.contains(key)) {
      Map(key -> params(key))
    } else {
      Map.empty[String, String]
    }

  def getMessage(locale: Locale, key: String, messages: Map[String, String]): String =
    messages.getOrElse(key, {
      //messages.foreach(kv => logger.info(s"getMessage() - $kv"))
      s"message___${locale}___${key}___is.not.available"
    } //
    )

  def getFailureNel(locale: Locale, key: String, messages: Map[String, String]): Validated[String] =
    getMessage(locale, key, messages).failureNel

}
