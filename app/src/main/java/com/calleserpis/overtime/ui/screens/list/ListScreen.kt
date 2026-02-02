package com.calleserpis.overtime.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.model.OvertimeCategory
import java.util.Map.entry

@Composable
fun ListScreen(
    onNavigateToDetail: (Long?) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
val stateList by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Top
    ) {
        stateList.items.forEach { entry ->
            OverTimeListItem(
                onNavigateToDetail = onNavigateToDetail, entry = entry
            )
        }



    }

}