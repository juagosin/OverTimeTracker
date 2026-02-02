package com.calleserpis.overtime.ui.screens.list

import com.calleserpis.overtime.domain.model.Overtime

data class ListState(
    val isLoading: Boolean = false,
    val isLoaded: Boolean = false,
    val items: List<Overtime> = emptyList(),
    val error: String? = null
)