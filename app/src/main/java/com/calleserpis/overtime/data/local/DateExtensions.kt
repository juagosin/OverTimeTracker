package com.calleserpis.overtime.data.local

import androidx.compose.ui.text.capitalize
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

fun String.toMonthRange(): Pair<Long, Long> {
    val parts = this.split("-")
    require(parts.size == 2) { "El formato debe ser YYYY-MM" }

    val year = parts[0].toInt()
    val month = parts[1].toInt()

    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val startDate = calendar.timeInMillis

    calendar.add(Calendar.MONTH, 1)
    val endDate = calendar.timeInMillis

    return Pair(startDate, endDate)
}
fun String.toMonthDayRange(): Pair<Long, Long> {
    val parts = this.split("-")
    require(parts.size == 3) { "El formato debe ser YYYY-MM-DD" }

    val year = parts[0].toInt()
    val month = parts[1].toInt()
    val day = parts[2].toInt()

    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val startDate = calendar.timeInMillis

    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val endDate = calendar.timeInMillis

    return Pair(startDate, endDate)
}
fun Long.toDayOfMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}
fun Long.toDayOfWeek(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return dateFormat.format(calendar.time)
}
fun Long.toMonthShortName(locale: Locale = Locale.getDefault()): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .month
        .getDisplayName(TextStyle.SHORT, locale).replaceFirstChar { it.uppercase() }
}
fun Long.toMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.MONTH) +1
}
fun Long.toHourMinute(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)

}

// Alternativa: función de extensión más clara
fun timeDifference(startTimestamp: Long, endTimestamp: Long): String {
    val duration = Duration.between(
        Instant.ofEpochMilli(startTimestamp),
        Instant.ofEpochMilli(endTimestamp)
    ).abs()

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60

    return String.format("%02dH %02dM", hours, minutes)
}