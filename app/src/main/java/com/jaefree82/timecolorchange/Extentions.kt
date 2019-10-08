package com.jaefree82.timecolorchange

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateFormat() : String {
    return try {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(this))
    } catch (e : Exception) {
        e.printStackTrace()
        ""
    }
}

fun Long.getTimeEvenOddNum() : Int {
    val date = this.toDateFormat()
    return date.last().toInt() % 2
}