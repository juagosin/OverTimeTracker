package com.calleserpis.overtime.ui.screens.calendar

import android.util.Log
import android.util.Log.e
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.overtime.domain.use_cases.OverTimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val overtimeUseCases: OverTimeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state.asStateFlow()
    private var loadEntriesJob: Job? = null
    private var loadDayEntriesJob: Job? = null
    init {

        dateSelected(LocalDate.now())

    }

    fun onEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.OnMonthChanged -> {
                monthChanged(event.month)
            }

            is CalendarEvent.OnDateSelected -> {
                dateSelected(event.date)

            }

        }
    }

    private fun dateSelected(date: LocalDate) {
        Log.d("CalendarViewModel", "Date selected: $date")
        loadDayEntriesJob?.cancel()
        loadDayEntriesJob = viewModelScope.launch {
            try{
                overtimeUseCases.getOvertimeEntriesByMonthDayUseCase(date.toString())
                    .collect { entries ->
                        _state.update { currentState ->
                            currentState.copy(dayEntries = entries)
                        }
                    }
            }
            catch (e: Exception){
                _state.update { it.copy(dayEntries = emptyList()) }
            }
        }

    }

    private fun monthChanged(month: YearMonth) {
        loadEntriesJob?.cancel()
        loadEntriesJob = viewModelScope.launch {

            try {

                overtimeUseCases.getOvertimeEntriesByMonthUseCase(month.toString())
                    .collect { entries ->
                        _state.update { currentState ->
                            currentState.copy(monthEntries = entries)
                        }
                    }


            } catch (e: Exception) {

                _state.update { it.copy(monthEntries = emptyList()) }
            }

        }

    }
}