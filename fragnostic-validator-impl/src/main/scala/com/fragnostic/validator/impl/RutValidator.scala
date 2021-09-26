package com.fragnostic.validator.impl

import com.fragnostic.validator.api._
import com.fragnostic.validator.support.ValidatorSupport
import scalaz.Scalaz._

import java.util.Locale

class RutValidator extends ValidatorApi[String] with ValidatorSupport {

  override def validate(locale: Locale, domain: String, rut: String, params: Map[String, String], messages: Map[String, String], mandatory: Boolean = true): Validated[String] =
    if (rut.trim.nonEmpty) {
      val rutFiltrado = rut.trim.filter(p => p.isDigit || p.toString.toLowerCase.equals(k)).toLowerCase
      val length = rutFiltrado.length
      if (length >= 8) {
        val base = rutFiltrado.substring(0, length - 1).toInt
        val dig = rutFiltrado.substring(length - 1)
        if (isValidContraDv(base, dig)) {
          s"$base-$dig".successNel
        } else {
          messages("rut.validator.rut.is.not.valid").failureNel
        }
      } else {
        messages("rut.validator.rut.is.not.valid").failureNel
      }
    } else {
      messages("rut.validator.rut.is.empty").failureNel
    }

  private lazy val k = "k"
  private lazy val rutNums = List(2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7)

  def calculaDigitoVerificador(rol: String): String = {
    val mult = (rol.reverseMap(_.asDigit).toList, rutNums).zipped.map(_ * _)
    val suma = (0 /: mult)(_ + _)
    val resto = suma % 11
    val diff = 11 - resto
    if (diff == 10) {
      k
    } else {
      diff.toString
    }
  }

  private def isValidContraDv(rol: Long, dv: String): Boolean =
    dv.equals(calculaDigitoVerificador(rol.toString))

}
