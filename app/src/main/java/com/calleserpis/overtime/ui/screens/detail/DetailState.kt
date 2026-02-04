package com.calleserpis.overtime.ui.screens.detail

data class DetailState(
    val id: Int = 0,
    val empresa: String = "",
    val detalles: String = "",
    val categoria: String = "",
    val date: Long? = System.currentTimeMillis(),
    val horaIni: String = "",
    val horaFin: String = "",


    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isSuccess: Boolean = false,
    val isSuccessDeleted: Boolean = false,
    val error: String? = null
)