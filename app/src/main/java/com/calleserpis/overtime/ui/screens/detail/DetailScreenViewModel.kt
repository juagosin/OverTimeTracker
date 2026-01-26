package com.calleserpis.overtime.ui.screens.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

fun onEvent(event: DetailEvent){
    when(event){
        is DetailEvent.OnEmpresaChanged ->{
            _state.update { it.copy(empresa = event.value) }
        }
    }
}

}