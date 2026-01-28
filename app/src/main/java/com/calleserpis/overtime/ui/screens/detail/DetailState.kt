package com.calleserpis.overtime.ui.screens.detail

data class DetailState(

    val empresa: String = "",
    val detalles: String = "",
    val date: Long? = System.currentTimeMillis(),
    val horaIni: String = "",
    val horaFin: String = "",


    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)