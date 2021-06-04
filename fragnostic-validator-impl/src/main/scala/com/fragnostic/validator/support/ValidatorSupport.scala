package com.fragnostic.validator.support

import com.fragnostic.i18n.api.ResourceI18n
import com.fragnostic.validator.i18n.ValidatorI18n

import java.util.Locale

trait ValidatorSupport {

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

}
