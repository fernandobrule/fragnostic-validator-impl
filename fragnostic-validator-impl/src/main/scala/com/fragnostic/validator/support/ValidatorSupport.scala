package com.fragnostic.validator.support

import com.fragnostic.validator.api.Validated
import scalaz.Scalaz._

trait ValidatorSupport {

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

  def getMessage(key: String, messages: Map[String, String]): String =
    messages.getOrElse(key, s"message___${key}___is.not.available")

  def getFailureNel(key: String, messages: Map[String, String]): Validated[String] =
    getMessage(key, messages).failureNel

}
