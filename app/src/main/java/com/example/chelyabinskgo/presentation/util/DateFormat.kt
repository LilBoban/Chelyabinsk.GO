package com.example.chelyabinskgo.presentation.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val ruDateTimeFormatter = DateTimeFormatter.ofPattern(
    "dd.MM.yyyy HH:mm",
    Locale("ru", "RU")
)

fun formatIsoDateTimeRu(isoString: String): String {
    return try {
        OffsetDateTime.parse(isoString).format(ruDateTimeFormatter)
    } catch (e: Exception) {
        isoString
    }
}
