package com.calleserpis.overtime.ui.screens.list

import android.R.attr.fontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.overtime.data.local.toDayOfWeek
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
        if (stateList.items.isEmpty()) {
            EmptyData()
        } else {
            stateList.items.forEach { entry ->
                OverTimeListItem(
                    onNavigateToDetail = onNavigateToDetail, entry = entry
                )
            }
        }


    }

}

@Composable
fun EmptyData() {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "No hay datos",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center


            )
        }
    }

}