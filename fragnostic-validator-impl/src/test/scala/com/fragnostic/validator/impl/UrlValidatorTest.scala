package com.fragnostic.validator.impl

import com.fragnostic.validator.api.Validated
import scalaz.{ Failure, NonEmptyList, Success }

class UrlValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("***URL Validator Test***") {

    val domain = "URL"
    val messages = Map(
      URL_VALIDATOR_URL_IS_EMPTY -> msgUrlIsEmpty,
      URL_VALIDATOR_URL_IS_NOT_VALID -> msgUrlIsNotValid //
    )

    it("Can Validate URL") {

      val urlValidator = new UrlValidator
      val params: Map[String, String] = Map("hasToFormat" -> "true")

      {
        val url = "http://google.com   "
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        assertResult(validation.isSuccess)(true)
        assertResult(validation.toList.head)(url.trim)
      }

      {
        val url = "    http://www.google.com"
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        assertResult(validation.isSuccess)(true)
        assertResult(validation.toList.head)(url.trim)
      }

      {
        val url = "   www.google.com    "
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        assertResult(validation.isSuccess)(true)
        assertResult(validation.toList.head)(url.trim)
      }

      {
        val url = "google.com"
        val validation: Validated[String] = urlValidator.validate(locale, domain, url, params, messages)
        assertResult(validation.isFailure)(true)
      }

      val validation: Validated[String] = urlValidator.validate(locale, domain, "", params, messages)

      assertResult(validation.isFailure)(true)
      assertResult((validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }))(msgUrlIsEmpty)

    }

  }

}