package com.calleserpis.overtime.ui.screens.detail

sealed class DetailEvent {
    data class OnEmpresaChanged(val value: String) : DetailEvent()
    data class OnDetallesChanged(val value: String) : DetailEvent()
    data class OnDateChanged(val value: Long) : DetailEvent()

    data class OnFechaIniChanged(val value: String) : DetailEvent()

    data class OnFechaFinChanged(val value: String) : DetailEvent()

    data class OnCategoriaChanged(val value: String) : DetailEvent()

    object OnSave : DetailEvent()

}