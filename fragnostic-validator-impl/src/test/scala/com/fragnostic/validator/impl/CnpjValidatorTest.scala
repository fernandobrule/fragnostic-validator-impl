package com.fragnostic.validator.impl

class CnpjValidatorTest extends AgnosticLifeCycleValidatorTest {

  describe("*** CnpjValidatorTest ***") {

    it("Can Validate Cnpj with dots") {
      val cnpj: String = "37.715.986/0001-96"
      def cnpjValidator = new CnpjValidator
      val cnpjValidated = cnpjValidator.validate(localePtBr, "Cnpj", cnpj, Map.empty, Map.empty) fold (
        nel => throw new IllegalStateException(nel.list.toList.mkString(", ")),
        cnpj => cnpj //
      )

      assertResult(cnpj)(cnpjValidated)
    }

    it("Can Validate Cnpj without dots") {
      val cnpj: String = "37715986000196"
      def cnpjValidator = new CnpjValidator
      val cnpjValidated = cnpjValidator.validate(localePtBr, "Cnpj", cnpj, Map.empty, Map.empty) fold (
        nel => throw new IllegalStateException(nel.list.toList.mkString(", ")),
        cnpj => cnpj //
      )

      assertResult(cnpj)(cnpjValidated)
    }

    it("Can Validate wrong Cnpj") {
      val cnpj: String = "37.715.987/0001-96"

      def cnpjValidator = new CnpjValidator
      val cnpjIsNotValid = "CNPJ is not valid"
      val messages: Map[String, String] = Map(
        MSG_CNPJ_VALIDATOR_CNPJ_IS_NOT_VALID -> cnpjIsNotValid)

      val error: String = cnpjValidator.validate(localePtBr, "Cnpj", cnpj, Map.empty, messages) fold (
        nel => nel.list.toList.mkString(", "),
        cnpj => cnpj //
      )

      assertResult(cnpjIsNotValid)(error)
    }

  }

}
