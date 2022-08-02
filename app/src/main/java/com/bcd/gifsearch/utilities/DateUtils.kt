package com.bcd.gifsearch.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatDate(timeStamp: Float): String {
        val sdf = SimpleDateFormat("dd/MM/YYY HH:mm:ss")
        return sdf.format(Date((timeStamp * 1000).toLong()))
    }
}