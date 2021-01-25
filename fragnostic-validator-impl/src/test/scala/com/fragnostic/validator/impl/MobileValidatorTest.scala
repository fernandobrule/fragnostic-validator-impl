package com.fragnostic.validator.impl

import scalaz.{ Failure, NonEmptyList, Success }

class MobileValidatorTest extends AgnosticLifeCycleValidatorTest with MobileValidator {

  val emptyTextMessage: String = "No ingresaste el Mobile"
  val errorMessage: String = "Mobile no válido"

  describe("Mobile Validator Test") {

    it("Can Validate Valid Mobile") {

      val mobile: String = " (11) 9 5197 6773   "
      val mobileRaw: String = "11951976773"

      val validation: StringValidation[String] = validateMobile(mobile, emptyTextMessage, errorMessage)
      validation.isSuccess should be(true)
      validation.toList.head should be(mobileRaw)

    }

    it("Can Validate Another Valid Mobile") {

      val mobile: String = " 11-951 976773"
      val mobileRaw: String = "11951976773"

      val validation: StringValidation[String] = validateMobile(mobile, emptyTextMessage, errorMessage)
      validation.isSuccess should be(true)
      validation.toList.head should be(mobileRaw)

    }

    it("Can Validate Empty CPF") {

      val cpf: String = "  "
      val validation: StringValidation[String] = validateMobile(cpf, emptyTextMessage, errorMessage)
      validation.isFailure should be(true)

      (validation match {
        case Failure(f) =>
          f match {
            case NonEmptyList(a, value) => a
            case _ =>
          }
        case Success(s) =>
      }) should be(emptyTextMessage)
    }

  }

}
