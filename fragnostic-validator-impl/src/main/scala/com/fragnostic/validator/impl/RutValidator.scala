package com.fragnostic.validator.impl

import com.fragnostic.validator.api.ValidatorApi
import scalaz.Scalaz._

trait RutValidator extends ValidatorApi[String] {

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

  override def validate(rut: String, hasToFormat: Boolean, messages: String*): Validated[String] = {
    if (messages.length < 3) {
      "rut.validator.messages.length.lt".failureNel
    } else if (messages.length > 3) {
      "rut.validator.messages.length.gt".failureNel
    } else if (rut.trim.nonEmpty) {
      val rutFiltrado = rut.trim.filter(p => p.isDigit || p.toString.toLowerCase.equals(k)).toLowerCase
      val lenght = rutFiltrado.length
      if (lenght >= 8) {
        val base = rutFiltrado.substring(0, lenght - 1).toInt
        val dig = rutFiltrado.substring(lenght - 1)
        if (isValidContraDv(base, dig)) {
          s"$base-$dig".successNel
        } else {
          messages(1).failureNel // rut.nv
        }
      } else {
        messages(2).failureNel // rut.mal.constituido
      }
    } else {
      messages(0).failureNel // rut.empty
    }
  }

}
