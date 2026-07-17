package com.calleserpis.overtime.ui.screens.summary

import com.calleserpis.overtime.domain.model.PeriodSummary
import com.calleserpis.overtime.domain.model.SummaryPeriodType
import com.calleserpis.overtime.domain.model.buildWeeklySummary
import java.time.LocalDate

data class SummaryState(
    val selectedPeriodType: SummaryPeriodType = SummaryPeriodType.WEEK,
    val referenceDate: LocalDate = LocalDate.now(),
    val summary: PeriodSummary = buildWeeklySummary(LocalDate.now(), emptyList()),
    val isLoading: Boolean = false,
    val error: String? = null
)
