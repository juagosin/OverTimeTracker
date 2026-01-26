package com.calleserpis.overtime.ui.screens.detail

sealed class DetailEvent {
    data class OnEmpresaChanged(val value: String) : DetailEvent()
    data class OnDetallesChanged(val value: String) : DetailEvent()
}