package com.calleserpis.overtime.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.overtime.R
import com.calleserpis.overtime.data.local.toDayOfMonth
import com.calleserpis.overtime.data.local.toMonth
import com.calleserpis.overtime.ui.screens.list.OverTimeListItem
import com.calleserpis.overtime.ui.theme.cobrada
import com.calleserpis.overtime.ui.theme.noCobrada
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateToDetail: (Long?) -> Unit
) {
    val stateCalendar by viewModel.state.collectAsStateWithLifecycle()


    Column() {
        Calendar(stateCalendar)
        Box(modifier = Modifier.padding(16.dp)) {
            Column() {
                if (stateCalendar.dayEntries.isNotEmpty()) {
                    stateCalendar.dayEntries.forEach { entry ->
                        OverTimeListItem(
                            onNavigateToDetail = onNavigateToDetail, entry
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Calendar(stateCalendar: CalendarState, viewModel: CalendarViewModel = hiltViewModel()) {

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(0) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,

        )
    var showMonthPicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.firstVisibleMonth.yearMonth) {

        viewModel.onEvent(CalendarEvent.OnMonthChanged(state.firstVisibleMonth.yearMonth))


    }
    LaunchedEffect(stateCalendar.monthEntries) {

        stateCalendar.monthEntries.forEach { entry ->

        }
    }

    Column(modifier = Modifier.background(colorScheme.surfaceContainer)) {
        // Encabezado del mes clickeable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showMonthPicker = true }
                .padding(bottom = 16.dp, top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.firstVisibleMonth.yearMonth.month
                    .getDisplayName(TextStyle.FULL, Locale.getDefault())
                    .replaceFirstChar { it.uppercase() } + " ${state.firstVisibleMonth.yearMonth.year}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Seleccionar mes",
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        HorizontalCalendar(
            state = state,
            dayContent = {
                Day(
                    it, stateCalendar,
                    onClick = {
                        viewModel.onEvent(CalendarEvent.OnDateSelected(it.date))
                    }
                )
            },
            monthHeader = {
                DaysOfWeekTitle(daysOfWeek = daysOfWeek) // Use the title as month header
            }
        )
    }


    // Diálogo selector de mes/año
    if (showMonthPicker) {
        MonthYearPickerDialog(
            currentMonth = state.firstVisibleMonth.yearMonth,
            startMonth = startMonth,
            endMonth = endMonth,
            onDismiss = { showMonthPicker = false },
            onMonthSelected = { selectedMonth ->
                coroutineScope.launch {
                    state.animateScrollToMonth(selectedMonth)
                    //viewModel.onEvent(CalendarEvent.OnMonthChanged(selectedMonth))

                }
                showMonthPicker = false
            }
        )
    }


}

@Composable
fun MonthYearPickerDialog(
    currentMonth: YearMonth,
    startMonth: YearMonth,
    endMonth: YearMonth,
    onDismiss: () -> Unit,
    onMonthSelected: (YearMonth) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_date),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Lista de meses y años
                val months = remember(startMonth, endMonth) {
                    buildList {
                        var current = startMonth
                        while (current <= endMonth) {
                            add(current)
                            current = current.plusMonths(1)
                        }
                    }
                }

                val listState = rememberLazyListState(
                    initialFirstVisibleItemIndex = months.indexOf(currentMonth).coerceAtLeast(0)
                )

                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f)
                ) {
                    items(months) { month ->
                        val isSelected = month == currentMonth

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) colorScheme.primaryContainer
                                    else colorScheme.surface
                                )
                                .clickable { onMonthSelected(month) }
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = month.month
                                    .getDisplayName(TextStyle.FULL, Locale.getDefault())
                                    .replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) colorScheme.onPrimaryContainer
                                else colorScheme.onSurface
                            )
                            Text(
                                text = month.year.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) colorScheme.onPrimaryContainer
                                else colorScheme.onSurface
                            )
                        }
                        if (month != months.last()) {
                            HorizontalDivider()
                        }
                    }
                }

                // Botón cerrar
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                ) {
                    Text(stringResource(R.string.txt_close))
                }
            }
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay, stateCalendar: CalendarState, onClick: (CalendarDay) -> Unit) {
    var colorbackground = if (day.position == DayPosition.MonthDate) {
        colorScheme.surfaceContainerLowest
    } else {
        colorScheme.surfaceContainer

    }
    var isToday = false
    if ((java.time.LocalDate.now().month == day.date.month)&& (
                day.date.dayOfMonth  == java.time.LocalDate.now().dayOfMonth)
            ) {
        isToday = true
        colorbackground = colorScheme.primaryContainer
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            )
            .background(colorbackground), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = day.date.dayOfMonth.toString())
            Row() {
                var showEmptyBox = true
                stateCalendar.monthEntries.forEach {
                    if ((it.dateIni.toDayOfMonth()
                            .toString() == day.date.dayOfMonth.toString()) && (it.dateIni.toMonth()
                            .toString() == day.date.monthValue.toString())
                    ) {
                        showEmptyBox = false
                        val colorCirculo =
                            if (it.categoria.toString() == "COBRADA") cobrada else noCobrada
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(horizontal = 1.dp)
                                .clip(CircleShape)
                                .background(
                                    colorCirculo
                                )
                        )
                    }

                }
                if (showEmptyBox) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(horizontal = 1.dp)
                            .clip(CircleShape)
                            .background(
                                Color.Transparent
                            )
                    )
                }
            }

        }
    }
}