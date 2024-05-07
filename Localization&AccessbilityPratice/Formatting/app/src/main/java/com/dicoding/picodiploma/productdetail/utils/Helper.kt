package com.dicoding.picodiploma.productdetail.utils

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.withNumberingFormat(): String {
    return NumberFormat.getNumberInstance().format(this.toDouble())
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance().format(date)
}

fun String.withCurrencyFormat(): String {
    val rupiah = 12495.95
    val euro = 0.88
    var dollar = this.toDouble() / rupiah

    var mCurrencyFormat = NumberFormat.getCurrencyInstance()
    val deviceLocale = Locale.getDefault().country
    when {
        deviceLocale.equals("ES") -> {
            dollar *= euro
        }

        deviceLocale.equals("ID") -> {
            dollar *= rupiah
        }

        else -> {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
        }
    }
    return mCurrencyFormat.format(dollar)
}