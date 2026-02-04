package com.calleserpis.overtime.ui.screens.detail

import android.util.Log
import android.util.Log.e
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.model.OvertimeCategory
import com.calleserpis.overtime.domain.use_cases.OverTimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val overtimeUseCases: OverTimeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()


    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    init {

        // Inicializamos la hora ini, hora fin y categoria por si no la toca el usuario
        _state.update { it.copy(horaIni = "18:00") }
        _state.update { it.copy(horaFin = "18:00") }
        _state.update { it.copy(categoria = "Pendiente") }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnEmpresaChanged -> {
                _state.update { it.copy(empresa = event.value) }
            }

            is DetailEvent.OnDetallesChanged -> {
                _state.update { it.copy(detalles = event.value) }
            }

            is DetailEvent.OnDateChanged -> {
                _state.update { it.copy(date = event.value) }
            }

            is DetailEvent.OnFechaIniChanged -> {
                _state.update { it.copy(horaIni = event.value) }
            }

            is DetailEvent.OnFechaFinChanged -> {
                _state.update { it.copy(horaFin = event.value) }
            }

            is DetailEvent.OnCategoriaChanged -> {
                _state.update { it.copy(categoria = event.value) }

            }

            is DetailEvent.OnSave -> {
                saveOverTime()
            }

            is DetailEvent.LoadOvertimeEntry -> {
                loadOvertimeEntry(event.id.toInt())

            }

            else -> {}
        }
    }

    private fun loadOvertimeEntry(id: Int) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                overtimeUseCases.getOvertimeEntryByIdUseCase(id).collect { overtime ->
                    overtime.let {
                        _state.update {
                            it.copy(
                                id = overtime?.id ?: 0,
                                empresa = overtime?.empresa ?: "",
                                detalles = overtime?.detalles ?: "",
                                date = overtime?.dateIni ?: 0L,
                                categoria = overtime?.categoria?.name ?: "",
                                horaIni = DateUtils.formatTime(overtime?.dateIni!!),
                                horaFin = DateUtils.formatTime(overtime.dateFin),
                                )

                        }

                    }
                }

            } catch (e: Exception) {
                Log.e("DetailScreenViewModel", "Error :", e)
            }
        }
        _state.update { it.copy(isLoading = false) }
    }

    private fun saveOverTime() {
        _state.update { it.copy(isSaving = true) }

        try {
            viewModelScope.launch {
                val state = _state.value
                val fechaInicioLong = calcularTimestamp(
                    fechaLong = state.date,
                    horaString = state.horaIni
                )

                var fechaFinLong = calcularTimestamp(
                    fechaLong = state.date,
                    horaString = state.horaFin
                )


                if (fechaFinLong!! <= fechaInicioLong!!) {
                    fechaFinLong =
                        fechaFinLong.plus(86_400_000L) // le sumamos 24h ya que la hora de fin es menor que la de inicio

                }
                overtimeUseCases.addOvertimeUseCase(
                    Overtime(
                        _state.value.id,
                        _state.value.empresa,
                        fechaInicioLong,
                        fechaFinLong,
                        OvertimeCategory.fromString(_state.value.categoria),
                        _state.value.detalles

                    )
                )
                var Overtime = Overtime(
                    _state.value.id,
                    _state.value.empresa,
                    fechaInicioLong,
                    fechaFinLong,
                    OvertimeCategory.fromString(_state.value.categoria),
                    _state.value.detalles

                )

                _state.update { it.copy(isSuccess = true) }

            }

        } catch (e: Exception) {
            Log.e("DetailScreenViewModel", "Error :", e)
            _state.update { it.copy(isSuccess = false) }
        }

        _state.update { it.copy(isSaving = false) }
    }


    private fun calcularTimestamp(fechaLong: Long?, horaString: String): Long? {
        if (fechaLong == null || horaString.isBlank()) return null

        return try {

            val localDate = LocalDateTime
                .ofInstant(
                    java.time.Instant.ofEpochMilli(fechaLong),
                    ZoneId.systemDefault()
                )
                .toLocalDate()


            val localTime = LocalTime.parse(horaString, timeFormatter)


            val localDateTime = LocalDateTime.of(localDate, localTime)


            localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception) {
            null
        }
    }

}