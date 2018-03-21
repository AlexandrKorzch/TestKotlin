package com.alex.kotlin.test.util

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by alex on 3/20/18.
 */
private val YYYY_MM_DD = "yyyy-MM-dd'T'HH:mm:ssZ"
private val YEAR_MONTH_DAY = "yyyy-MM-dd"

fun formatDate(expDate: String?): String { //2017-12-23T08:41:21Z
    var formattedDate:String
    try {

        val yearFirstFormat = SimpleDateFormat(YYYY_MM_DD, Locale.US)
        val date = yearFirstFormat.parse(expDate)
        formattedDate = SimpleDateFormat(YEAR_MONTH_DAY, Locale.US).format(date.getTime())
    } catch (e: Exception) {
        e.printStackTrace()
        formattedDate = expDate?.split("T".toRegex())?.dropLastWhile ({ it.isEmpty() })?.toTypedArray()!![0]
    }

    return formattedDate
}