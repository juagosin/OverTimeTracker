package com.calleserpis.overtime.data.local

import java.time.Instant
import java.time.ZoneId
import java.util.Calendar

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
fun Long.toDayOfMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}
fun Long.toMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.MONTH) +1
}
