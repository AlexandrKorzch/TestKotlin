package com.alex.kotlin.test.util

import java.text.SimpleDateFormat
import java.util.*


private const val YYYY_MM_DD = "yyyy-MM-dd'T'HH:mm:ssZ"
private const val YEAR_MONTH_DAY = "yyyy-MM-dd"

fun formatDate(expDate: String?): String { //2017-12-23T08:41:21Z

    return try {
        val yearFirstFormat = SimpleDateFormat(YYYY_MM_DD, Locale.US)
        val date = yearFirstFormat.parse(expDate)
        SimpleDateFormat(YEAR_MONTH_DAY, Locale.US).format(date.time)
    } catch (e: Exception) {
        e.printStackTrace()
        expDate?.split("T".toRegex())?.dropLastWhile ({ it.isEmpty() })?.toTypedArray()!![0]
    }
}