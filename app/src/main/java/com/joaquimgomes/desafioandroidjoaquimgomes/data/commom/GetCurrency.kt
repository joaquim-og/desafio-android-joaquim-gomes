package com.joaquimgomes.desafioandroidjoaquimgomes.data.commom

import java.text.NumberFormat
import java.util.*

class GetCurrency {

    fun localeCurrency(): NumberFormat {

        val localeNumberFormatter = NumberFormat.getCurrencyInstance()
        localeNumberFormatter.maximumFractionDigits = 2

        localeNumberFormatter.currency = Currency.getInstance(Locale.US)

        return localeNumberFormatter
    }

}