package com.calleserpis.overtime.ui.screens.calendar

import com.calleserpis.overtime.domain.model.Overtime
import java.time.LocalDate

data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedMonth: LocalDate = LocalDate.now(),
    val monthEntries: List<Overtime> = emptyList(),
    val dayEntries: List<Overtime> = emptyList(),

    val isLoading: Boolean = false,
    val isLoaded: Boolean = false,
    val error: String? = null
)



