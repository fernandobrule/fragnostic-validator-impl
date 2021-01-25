package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import scalaz.Scalaz._

import java.util.Locale

trait RutValidator extends UnderValidation {

  def validateRut(rut: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] =
    if (rut.trim.isEmpty) {
      errorMessage.failureNel
    } else {
      rut.trim.successNel
    }

  private lazy val k = "k"
  private lazy val rutNums = List(2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7)

  def calculaDigitoVerificador(rol: String): String = {
    val mult = (rol.reverseMap(_.asDigit).toList, rutNums).zipped.map(_ * _)
    val suma = (0 /: mult)(_ + _)
    val resto = suma % 11
    val diff = 11 - resto
    if (diff == 10) {
      "k"
    } else {
      diff.toString
    }
  }

  private def isValidContraDv(rol: Long, dv: String): Boolean =
    dv.equals(calculaDigitoVerificador(rol.toString))

  def sdfsd(locale: Locale, rut: String, args: Map[String, String]): Either[List[String], String] = {
    if (rut.trim.nonEmpty) {

      val rutFiltrado = rut.trim.filter(p => p.isDigit || p.toString.toLowerCase.equals(k)).toLowerCase

      val lenght = rutFiltrado.length

      if (lenght >= 8) {
        val base = rutFiltrado.substring(0, lenght - 1).toInt
        val dig = rutFiltrado.substring(lenght - 1)
        if (isValidContraDv(base, dig)) {
          Right(s"$base-$dig")
        } else {
          Left(List("rut.nv"))
        }

      } else {
        Left(List("rut.mal.constituido"))
      }

    } else {
      Left(List("rut.empty"))
    }
  }

}
