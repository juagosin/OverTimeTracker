package com.calleserpis.overtime.ui.screens.calendar

import java.time.LocalDate
import java.time.YearMonth

sealed class CalendarEvent {
    data class OnMonthChanged(val month: YearMonth) : CalendarEvent()
    data class OnDateSelected(val date: LocalDate) : CalendarEvent()

}