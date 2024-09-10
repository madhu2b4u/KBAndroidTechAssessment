package com.example.kbandroidtechassessment.transaction

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateFormats {
    const val DD_MMM_YYYY = "dd-MMM-yyyy"
    const val YYYY_MM_DD = "yyyy-MM-dd"
}

fun formatDate(input: Any?): String {
    val dateFormat = SimpleDateFormat(DateFormats.DD_MMM_YYYY, Locale.getDefault())

    return when (input) {
        is Long -> {
            if (input > 0) dateFormat.format(Date(input)) else ""
        }

        is String -> {
            val inputFormat = SimpleDateFormat(DateFormats.YYYY_MM_DD, Locale.getDefault())
            try {
                val parsedDate = inputFormat.parse(input)
                parsedDate?.let { dateFormat.format(it) } ?: input
            } catch (e: Exception) {
                input // Return original string if parsing fails
            }
        }

        else -> ""
    }
}
