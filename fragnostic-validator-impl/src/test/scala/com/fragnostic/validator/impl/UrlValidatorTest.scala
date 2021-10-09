package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class UrlValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***URL Validator Test***") {

    val domain = "URL"
    val messages = Map(
      "url.validator.url.is.empty" -> msgUrlIsEmpty,
      "url.validator.url.is.not.valid" -> msgUrlIsNotValid //
    )

    it("Can Validate URL") {

      val urlValidator = new UrlValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")

      {
        val url = "http://google.com   "
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        validation.isSuccess should be(true)
        validation.toList.head should be(url.trim)
      }

      {
        val url = "    http://www.google.com"
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        validation.isSuccess should be(true)
        validation.toList.head should be(url.trim)
      }

      {
        val url = "   www.google.com    "
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        validation.isSuccess should be(true)
        validation.toList.head should be(url.trim)
      }

      {
        val url = "google.com"
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        validation.isFailure should be(true)
      }

      val validation: Validated[String] = urlValidator.validate(locale, domain, "", params, messages)

      validation.isFailure should be(true)
      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(msgUrlIsEmpty)

    }

  }

}