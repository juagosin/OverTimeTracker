package com.calleserpis.overtime.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.model.OvertimeCategory
import java.util.Map.entry

@Composable
fun ListScreen(onNavigateToDetail: (Long?) -> Unit) {

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Top
    ) {
        OverTimeListItem(
            onNavigateToDetail = onNavigateToDetail,
            entry = Overtime(
                id = 1,
                empresa = "Empresa 1",
                dateIni = 1769544000000L,
                dateFin = 1769551200000L,
                detalles = "",
                categoria = OvertimeCategory.COBRADA)

            )
        OverTimeListItem(
            onNavigateToDetail = onNavigateToDetail,
            entry = Overtime(
                id = 1,
                empresa = "Empresa 2",
                dateIni = 1769544000000L,
                dateFin = 1769551200000L,
                detalles = "",
                categoria = OvertimeCategory.PENDIENTE)

        )
        OverTimeListItem(
            onNavigateToDetail = onNavigateToDetail,
            entry = Overtime(
                id = 1,
                empresa = "Empresa 3",
                dateIni = 1769544000000L,
                dateFin = 1769551200000L,
                detalles = "",
                categoria = OvertimeCategory.COBRADA)

        )

    }

}