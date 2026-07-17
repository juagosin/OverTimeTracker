package com.calleserpis.overtime.ui.screens.summary

import com.calleserpis.overtime.domain.model.SummaryPeriodType

sealed class SummaryEvent {
    data class OnPeriodTypeChanged(val periodType: SummaryPeriodType) : SummaryEvent()
    data object OnPreviousPeriod : SummaryEvent()
    data object OnNextPeriod : SummaryEvent()
}
