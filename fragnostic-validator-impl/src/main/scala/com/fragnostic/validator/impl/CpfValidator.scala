package com.fragnostic.validator.impl

import com.fragnostic.validator.glue.UnderValidation
import org.slf4j.{ Logger, LoggerFactory }
import scalaz.Scalaz._

trait CpfValidator extends UnderValidation {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  private def isAllDigits(x: String) = x forall Character.isDigit

  def validateCpf(cpf: String, emptyTextMessage: String, errorMessage: String): StringValidation[String] = {
    // TODO esta es una implementación absolutamente mínima
    logger.warn("validateCpf() - esta es una implementación absolutamente mínima")
    if (cpf.isEmpty) {
      emptyTextMessage.failureNel
    } else if (!isAllDigits(cpf)) {
      errorMessage.failureNel
    } else {
      cpf.trim.successNel
    }
  }

  /*
//
// https://raw.githubusercontent.com/carvalhoviniciusluiz/cpf-cnpj-validator/master/src/cpf.ts
  // Blacklist common values.
  val BLACKLIST: Array[String] = Array(
  "00000000000",
  "11111111111",
  "22222222222",
  "33333333333",
  "44444444444",
  "55555555555",
  "66666666666",
  "77777777777",
  "88888888888",
  "99999999999",
  "12345678909"
  )

  val STRICT_STRIP_REGEX: RegExp = /[.-]/g
  val LOOSE_STRIP_REGEX: RegExp = /[^\d]/g

  val verifierDigit = (digits: string): Int => {
    const numbers: Array<number> = digits
    .split('')
    .map(number => {
    return parseInt(number, 10)
  })

    const modulus: number = numbers.length + 1
    const multiplied: Array<number> = numbers.map((number, index) => number * (modulus - index))
    const mod: number = multiplied.reduce((buffer, number) => buffer + number) % 11

    return (mod < 2 ? 0 : 11 - mod)
  }

  const strip = (number: string, strict?: boolean): string => {
    const regex: RegExp = strict ? STRICT_STRIP_REGEX : LOOSE_STRIP_REGEX
    return (number || '').replace(regex, '')
  }

  const format = (number: string): string => {
    return strip(number).replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, '$1.$2.$3-$4')
  }

  const isValid = (number: string, strict?: boolean): boolean => {
    const stripped: string = strip(number, strict)

    // CPF must be defined
    if (!stripped) {
    return false
  }

    // CPF must have 11 chars
    if (stripped.length !== 11) {
    return false
  }

    // CPF can't be blacklisted
    if (BLACKLIST.includes(stripped)) {
    return false
  }

    let numbers: string = stripped.substr(0, 9)
    numbers += verifierDigit(numbers)
    numbers += verifierDigit(numbers)

    return numbers.substr(-2) === stripped.substr(-2)
  }

  const generate = (formatted?: boolean): string => {
    let numbers: string = ''

    for (let i = 0; i < 9; i += 1) {
    numbers += Math.floor(Math.random() * 9)
  }

    numbers += verifierDigit(numbers)
    numbers += verifierDigit(numbers)

    return (formatted ? format(numbers) : numbers)
  }

  export default {
    verifierDigit,
    strip,
    format,
    isValid,
    generate,
  }
*/
}
