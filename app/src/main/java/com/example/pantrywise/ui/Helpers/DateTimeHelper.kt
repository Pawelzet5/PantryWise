package com.example.pantrywise.ui.Helpers

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object DateTimeHelper {
    fun daysBetweenTimestampAndNow(timestampMillis: Long): Int {
        val zoneId = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(timestampMillis).atZone(zoneId).toLocalDate()
        val today = LocalDate.now(zoneId)
        return ChronoUnit.DAYS.between(date, today).toInt()
    }
} 