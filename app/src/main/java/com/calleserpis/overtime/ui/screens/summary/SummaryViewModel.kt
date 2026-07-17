package com.calleserpis.overtime.ui.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.overtime.domain.model.SummaryPeriodType
import com.calleserpis.overtime.domain.model.buildMonthlySummary
import com.calleserpis.overtime.domain.model.buildWeeklySummary
import com.calleserpis.overtime.domain.use_cases.OverTimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val overtimeUseCases: OverTimeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SummaryState())
    val state: StateFlow<SummaryState> = _state.asStateFlow()

    private var loadSummaryJob: Job? = null

    init {
        loadSummary()
    }

    fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.OnPeriodTypeChanged -> {
                _state.update {
                    it.copy(
                        selectedPeriodType = event.periodType,
                        referenceDate = LocalDate.now()
                    )
                }
                loadSummary()
            }

            SummaryEvent.OnPreviousPeriod -> {
                _state.update {
                    it.copy(referenceDate = movePeriod(it.referenceDate, it.selectedPeriodType, -1))
                }
                loadSummary()
            }

            SummaryEvent.OnNextPeriod -> {
                _state.update {
                    it.copy(referenceDate = movePeriod(it.referenceDate, it.selectedPeriodType, 1))
                }
                loadSummary()
            }
        }
    }

    private fun loadSummary() {
        val currentState = _state.value
        loadSummaryJob?.cancel()
        _state.update { it.copy(isLoading = true, error = null) }
        loadSummaryJob = viewModelScope.launch {
            try {
                when (currentState.selectedPeriodType) {
                    SummaryPeriodType.WEEK -> {
                        overtimeUseCases.getWeeklySummaryUseCase(currentState.referenceDate)
                            .collect { summary ->
                                _state.update {
                                    it.copy(summary = summary, isLoading = false)
                                }
                            }
                    }

                    SummaryPeriodType.MONTH -> {
                        overtimeUseCases.getMonthlySummaryUseCase(currentState.referenceDate)
                            .collect { summary ->
                                _state.update {
                                    it.copy(summary = summary, isLoading = false)
                                }
                            }
                    }
                }
            } catch (e: Exception) {
                val emptySummary = when (currentState.selectedPeriodType) {
                    SummaryPeriodType.WEEK -> buildWeeklySummary(currentState.referenceDate, emptyList())
                    SummaryPeriodType.MONTH -> buildMonthlySummary(currentState.referenceDate, emptyList())
                }
                _state.update {
                    it.copy(
                        summary = emptySummary,
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun movePeriod(
        referenceDate: LocalDate,
        periodType: SummaryPeriodType,
        amount: Long
    ): LocalDate {
        return when (periodType) {
            SummaryPeriodType.WEEK -> referenceDate.plusWeeks(amount)
            SummaryPeriodType.MONTH -> referenceDate.plusMonths(amount)
        }
    }
}
