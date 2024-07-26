package com.jcb.koinapp.presentation.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun String.toMedium(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    val localDate = LocalDate.parse(this, inputFormatter)
    return outputFormatter.format(localDate)
}