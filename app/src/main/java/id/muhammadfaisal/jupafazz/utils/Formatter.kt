package id.muhammadfaisal.jupafazz.utils

import id.muhammadfaisal.jupafazz.helper.GeneralHelper
import id.muhammadfaisal.jupafazz.ui.RupiahEditText
import java.text.NumberFormat
import java.util.*

class Formatter {
    companion object {
        fun currency(value: Long, locale: String, isUsePrefix: Boolean = true) : String {
            val format = NumberFormat.getCurrencyInstance(Locale(locale, locale)).format(value).toString()
            val results = format.split(",")
            var result = results[0]
            if (!isUsePrefix) {
                result = result.replace("Rp", "")
                return result
            }

            return result
        }

        fun queryFormat(s: String): String {
            return "%$s%"
        }

        fun plainCurrency(rpEditText: RupiahEditText) : String {
            if (GeneralHelper.isInputEmpty(rpEditText)) {
                return ""
            }

            return rpEditText.text.toString().replace(".", "")
        }
    }
}