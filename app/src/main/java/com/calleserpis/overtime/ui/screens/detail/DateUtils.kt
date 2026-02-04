package com.calleserpis.overtime.ui.screens.detail

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

    fun formatDate(timeInMillis: Long, pattern: String): String {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            // IMPORTANTE: Usar zona horaria del sistema
            sdf.timeZone = TimeZone.getDefault()
            sdf.format(Date(timeInMillis))
        } catch (e: Exception) {
            ""
        }
    }

    fun formatDateTime(
        timestamp: Long,
        pattern: String = "dd/MM/yyyy HH:mm",
        locale: Locale = Locale.getDefault()
    ): String {
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timestamp))
    }

    // Otros formatos comunes
    fun formatTime(timestamp: Long): String =
        formatDate(timestamp, "HH:mm")

    fun formatHour(timestamp: Long): String =
        formatDate(timestamp, "HH")
    fun formatMinute(timestamp: Long): String =
        formatDate(timestamp, "mm")

    fun formatShortDate(timestamp: Long): String =
        formatDate(timestamp, "dd/MM/yy")

}