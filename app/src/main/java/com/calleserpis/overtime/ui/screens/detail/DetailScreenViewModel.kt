package com.calleserpis.overtime.ui.screens.detail

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()


    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    init {
     // Inicializamos la hora ini y hora fin por si no la toca el usuario
        _state.update { it.copy(horaIni = "18:00")}
        _state.update { it.copy(horaFin = "18:00") }
    }
fun onEvent(event: DetailEvent){
    when(event){
        is DetailEvent.OnEmpresaChanged ->{
            _state.update { it.copy(empresa = event.value) }
        }
        is DetailEvent.OnDetallesChanged ->{
            _state.update { it.copy(detalles = event.value) }
        }
        is DetailEvent.OnDateChanged ->{
            _state.update { it.copy(date = event.value) }
        }
        is DetailEvent.OnFechaIniChanged -> {
            _state.update { it.copy(horaIni = event.value) }
        }
        is DetailEvent.OnFechaFinChanged -> {
            _state.update { it.copy(horaFin = event.value) }
        }
        is DetailEvent.OnSave ->{
            saveOverTime()
        }

        else -> {}
    }
}

    private fun saveOverTime() {
        _state.update { it.copy(isSaving = true) }

        try {
            val state = _state.value
            val fechaInicioLong = calcularTimestamp(
                fechaLong = state.date,
                horaString = state.horaIni
            )

            var fechaFinLong = calcularTimestamp(
                fechaLong = state.date,
                horaString = state.horaFin
            )


            if(fechaFinLong!! <= fechaInicioLong!!){
                fechaFinLong = fechaFinLong.plus(86_400_000L) // le sumamos 24h ya que la hora de fin es menor que la de inicio

            }




        }
        catch (e:Exception) {
            Log.e("DetailScreenViewModel", "Error al calcular timestamps", e)

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