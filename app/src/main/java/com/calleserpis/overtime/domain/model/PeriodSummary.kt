package com.calleserpis.overtime.domain.model

import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

enum class SummaryPeriodType {
    WEEK,
    MONTH
}

data class PeriodSummary(
    val periodType: SummaryPeriodType,
    val periodLabel: String,
    val periodDescription: String,
    val referenceDate: LocalDate,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val entries: List<Overtime>,
    val totalEntries: Int,
    val totalDurationMinutes: Long,
    val totalMoney: Double,
    val pendingDurationMinutes: Long,
    val paidDurationMinutes: Long,
    val pendingMoney: Double,
    val paidMoney: Double
)

fun buildWeeklySummary(referenceDate: LocalDate, entries: List<Overtime>): PeriodSummary {
    val startDate = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endDate = referenceDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val weekFormatter = DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault())
    val rangeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())

    return buildPeriodSummary(
        periodType = SummaryPeriodType.WEEK,
        periodLabel = "${startDate.format(weekFormatter)} - ${endDate.format(weekFormatter)}",
        periodDescription = "${startDate.format(rangeFormatter)} - ${endDate.format(rangeFormatter)}",
        referenceDate = referenceDate,
        startDate = startDate,
        endDate = endDate,
        entries = entries
    )
}

fun buildMonthlySummary(referenceDate: LocalDate, entries: List<Overtime>): PeriodSummary {
    val startDate = referenceDate.withDayOfMonth(1)
    val endDate = referenceDate.withDayOfMonth(referenceDate.lengthOfMonth())
    val monthName = startDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        .replaceFirstChar { it.uppercase() }
    val rangeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())

    return buildPeriodSummary(
        periodType = SummaryPeriodType.MONTH,
        periodLabel = "$monthName ${startDate.year}",
        periodDescription = "${startDate.format(rangeFormatter)} - ${endDate.format(rangeFormatter)}",
        referenceDate = referenceDate,
        startDate = startDate,
        endDate = endDate,
        entries = entries
    )
}

fun weeklyRangeMillis(referenceDate: LocalDate): Pair<Long, Long> {
    val startDate = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endDateExclusive = referenceDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1)
    return startDate.toEpochMillis() to endDateExclusive.toEpochMillis()
}

fun monthlyRangeMillis(referenceDate: LocalDate): Pair<Long, Long> {
    val startDate = referenceDate.withDayOfMonth(1)
    val endDateExclusive = referenceDate.withDayOfMonth(referenceDate.lengthOfMonth()).plusDays(1)
    return startDate.toEpochMillis() to endDateExclusive.toEpochMillis()
}

fun Overtime.durationMinutes(): Long {
    return Duration.between(
        Instant.ofEpochMilli(dateIni),
        Instant.ofEpochMilli(dateFin)
    ).toMinutes().coerceAtLeast(0)
}

fun Long.toDurationLabel(): String {
    val hours = this / 60
    val minutes = this % 60
    return String.format("%02dH %02dM", hours, minutes)
}

fun Double.toMoneyLabel(): String {
    return "${DecimalFormat("0.00").format(this)} €"
}

private fun buildPeriodSummary(
    periodType: SummaryPeriodType,
    periodLabel: String,
    periodDescription: String,
    referenceDate: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
    entries: List<Overtime>
): PeriodSummary {
    val totalDurationMinutes = entries.sumOf { it.durationMinutes() }
    val pendingEntries = entries.filter { it.categoria == OvertimeCategory.PENDIENTE }
    val paidEntries = entries.filter { it.categoria == OvertimeCategory.COBRADA }

    return PeriodSummary(
        periodType = periodType,
        periodLabel = periodLabel,
        periodDescription = periodDescription,
        referenceDate = referenceDate,
        startDate = startDate,
        endDate = endDate,
        entries = entries,
        totalEntries = entries.size,
        totalDurationMinutes = totalDurationMinutes,
        totalMoney = entries.sumOf { it.dinero },
        pendingDurationMinutes = pendingEntries.sumOf { it.durationMinutes() },
        paidDurationMinutes = paidEntries.sumOf { it.durationMinutes() },
        pendingMoney = pendingEntries.sumOf { it.dinero },
        paidMoney = paidEntries.sumOf { it.dinero }
    )
}

private fun LocalDate.toEpochMillis(): Long {
    return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
