package com.fragnostic.validator.i18n

import com.fragnostic.i18n.impl.AbstractSingleMessageI18n

class ValidatorI18n extends AbstractSingleMessageI18n {

  override def baseDir: String = "VALIDATOR_I18N_BASE_DIR"
  override def baseName: String = "VALIDATOR_I18N_BASE_NAME"

}

