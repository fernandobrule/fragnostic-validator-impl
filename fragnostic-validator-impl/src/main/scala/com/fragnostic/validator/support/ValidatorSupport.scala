package com.fragnostic.validator.support

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.api.Validated
import com.fragnostic.validator.i18n.ValidatorI18n
import scalaz.Scalaz._
import java.util.Locale

trait ValidatorSupport {

  val idxTextEmpty: Int = 0
  val idxTextNotValid: Int = 1
  val idxTextLengthier: Int = 2
  val idxCountryCode: Int = 3
  val idxTextShorter: Int = 4
  val idxPasswordMustHaveAtLeastOneUppercaseLetter: Int = 5
  val idxPasswordMustHaveAtLeastOneLowercaseLetter: Int = 6
  val idxPasswordMustHaveAtLeastOneNumber: Int = 7
  val idxPasswordMustHaveAtLeastOneSymbol: Int = 8

  def validatorI18n = new ValidatorI18n()

  def argsAreValid(numberExpected: Int, messages: List[String]): Boolean =
    numberExpected == messages.length

  def getErrorMessage(locale: Locale, key: String, args: List[String], i18n: ResourceI18n, idx: Int, messages: List[String]): String =
    if (messages.nonEmpty && messages.length > idx) {
      messages(idx)
    } else if (args.isEmpty) {
      i18n.getString(locale, key)
    } else {
      i18n.getFormattedString(locale, key, args)
    }

  private def lookForKey(params: Map[String, String], head: (String, String), newMap: Map[String, String]): Map[String, String] = {
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

  def lookForKeys(params: Map[String, String], keyDomain: List[(String, String)], newMap: Map[String, String]): Map[String, String] =
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

  def _ifMandatory(locale: Locale, key: String, messages: List[String], mandatory: Boolean): Validated[String] =
    if (mandatory) {
      getErrorMessage(locale, key, Nil, validatorI18n, idxTextEmpty, messages).failureNel
    } else {
      "".successNel
    }

}
