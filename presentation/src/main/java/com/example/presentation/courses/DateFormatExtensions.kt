package com.example.presentation.courses

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val russianLocale = Locale.forLanguageTag("ru-RU")

private val inputDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
private val outputDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", russianLocale)

fun String.toRuReadableDate(): String = try {
    val date = LocalDate.parse(this, inputDateFormatter)
    date.format(outputDateFormatter)
} catch (_: Exception) {
    this
}
