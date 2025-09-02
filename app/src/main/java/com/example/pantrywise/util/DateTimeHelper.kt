package com.example.pantrywise.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object DateTimeHelper {
    fun daysBetweenTimestampAndNow(timestampMillis: Long): Int {
        val zoneId = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(timestampMillis).atZone(zoneId).toLocalDate()
        val today = LocalDate.now(zoneId)
        return ChronoUnit.DAYS.between(today, date).toInt()
    }
}