package com.calleserpis.overtime.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calleserpis.overtime.domain.use_cases.OverTimeUseCases
import com.calleserpis.overtime.ui.screens.calendar.CalendarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val overtimeUseCases: OverTimeUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(ListState())
    val state: StateFlow<ListState> = _state.asStateFlow()
    private var loadEntriesJob: Job? = null
    init {
        loadEntries()
    }

    private fun loadEntries() {
        loadEntriesJob?.cancel()
        loadEntriesJob = viewModelScope.launch {
            try{
                overtimeUseCases.getOvertimeEntriesUseCase()
                    .collect { entries ->
                        _state.update { currentState ->
                            currentState.copy(items = entries)
                        }
                    }
            }
            catch (e: Exception){
                _state.update { it.copy(items = emptyList()) }
            }
        }
    }
}

