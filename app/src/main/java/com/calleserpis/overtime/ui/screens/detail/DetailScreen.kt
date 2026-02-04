package com.calleserpis.overtime.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.overtime.ui.theme.cobrada
import com.calleserpis.overtime.ui.theme.noCobrada

@Composable
fun DetailScreen(
    recordId: Long? = null,
    viewModel: DetailScreenViewModel = hiltViewModel(), onOverTimeSaved: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("Pendiente") }
    var selectedTime by remember { mutableStateOf("18:00") }



    val state by viewModel.state.collectAsStateWithLifecycle()
    var initialHourIni by remember { mutableIntStateOf(18) }
    var initialMinuteIni by remember { mutableIntStateOf(0) }
    var initialHourFin by remember { mutableIntStateOf(19) }
    var initialMinuteFin by remember { mutableIntStateOf(0) }

    if (state.isSuccess) {
        onOverTimeSaved()
    }
    LaunchedEffect(recordId) {
        if (recordId != null) {
            viewModel.onEvent(DetailEvent.LoadOvertimeEntry(recordId))

        }
    }
    LaunchedEffect(state.horaIni, state.horaFin) {
        if (recordId != null && state.horaIni.isNotBlank() && state.horaFin.isNotBlank()) {
            state.horaIni.split(":").takeIf { it.size == 2 }?.let { parts ->
                initialHourIni = parts[0].toIntOrNull() ?: 18
                initialMinuteIni = parts[1].toIntOrNull() ?: 0
            }

            state.horaFin.split(":").takeIf { it.size == 2 }?.let { parts ->
                initialHourFin = parts[0].toIntOrNull() ?: 8
                initialMinuteFin = parts[1].toIntOrNull() ?: 0
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = state.empresa,
            onValueChange = {
                viewModel.onEvent(DetailEvent.OnEmpresaChanged(it))
            },
            label = { Text("Empresa") },
            modifier = Modifier.fillMaxWidth()

        )
        DatePickerTextField(
            value = state.date,
            onValueChange = {
                viewModel.onEvent(DetailEvent.OnDateChanged(it))
            },
            label = "",
            modifier = Modifier.fillMaxWidth(),

            )
        //spinners de hora ini y hora fin
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column() {
                Text("Hora Inicio")
                TimePicker(
                    initialHour = initialHourIni,
                    initialMinute = initialMinuteIni,
                    onTimeChanged = { hour, minute ->
                        selectedTime = String.format("%02d:%02d", hour, minute)
                        viewModel.onEvent(DetailEvent.OnFechaIniChanged(selectedTime))

                    }
                )
            }
            Column() {
                Text("Hora Fin")
                TimePicker(
                    initialHour = initialHourFin,
                    initialMinute = initialMinuteFin,
                    onTimeChanged = { hour, minute ->
                        selectedTime = String.format("%02d:%02d", hour, minute)
                        viewModel.onEvent(DetailEvent.OnFechaFinChanged(selectedTime))


                    }
                )
            }
        }
        OutlinedTextField(
            value = state.detalles,
            onValueChange = {
                viewModel.onEvent(DetailEvent.OnDetallesChanged(it))
            },
            placeholder = { Text("Noche, fin de semana, notas...") }, //TODO ->Poner el texto en clarito
            label = { Text("Concepto") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 3,

            )
        Spacer(modifier = Modifier.height(4.dp))
        SegmentedSwitch(
            selectedOption = selectedOption,
            onOptionSelected = {
                selectedOption = it
                viewModel.onEvent(DetailEvent.OnCategoriaChanged(selectedOption))
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = {
                viewModel.onEvent(DetailEvent.OnSave)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        if (recordId != null) {
            OutlinedButton(
                onClick = {
                    viewModel.onEvent(DetailEvent.OnSave)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.errorContainer,
                    contentColor = colorScheme.onError
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borrar", color = colorScheme.error)
            }
        }
    }
}

@Composable
fun SegmentedSwitch(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    option1: String = "Cobrada",
    option2: String = "Pendiente",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier

            .border(1.dp, colorScheme.outline, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        SegmentedButton(
            text = option1,
            isSelected = selectedOption == option1,
            onClick = { onOptionSelected(option1) },
            modifier = Modifier.weight(1f)
        )

        //Spacer(modifier = Modifier.width(4.dp))

        SegmentedButton(
            text = option2,
            isSelected = selectedOption == option2,
            onClick = { onOptionSelected(option2) },
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
private fun SegmentedButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorButton = if (text == "Cobrada") cobrada else noCobrada
    Box(
        modifier = modifier

            .background(
                if (isSelected) colorButton
                else colorButton.copy(alpha=0.1f)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) colorScheme.onPrimary else colorScheme.onSurface,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TimePicker(
    initialHour: Int,
    initialMinute: Int,
    onTimeChanged: (hour: Int, minute: Int) -> Unit = { _, _ -> }
) {
    var hour by remember(initialHour) { mutableStateOf(initialHour) }
    var minute by remember(initialMinute) { mutableStateOf(initialMinute) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        // Selector de Horas
        TimePickerColumn(
            value = hour,
            onValueChange = { newHour ->
                hour = if (newHour > 23) 0 else if (newHour < 0) 23 else newHour
                onTimeChanged(hour, minute)
            }
        )

        // Separador
        Text(
            text = ":",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Selector de Minutos (incrementos de 15)
        TimePickerColumn(
            value = minute,
            onValueChange = { newMinute ->
                minute = when {
                    newMinute >= 60 -> 0
                    newMinute < 0 -> 45
                    else -> newMinute
                }
                onTimeChanged(hour, minute)
            },
            step = 15
        )

    }
}


@Composable
fun TimePickerColumn(
    value: Int,
    onValueChange: (Int) -> Unit,
    step: Int = 1
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(40.dp)
    ) {
        // Botón Incrementar
        IconButton(onClick = { onValueChange(value + step) }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Incrementar",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        // Divisor superior
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        )

        // Valor
        Text(
            text = String.format("%02d", value),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Divisor inferior
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        )

        // Botón Decrementar
        IconButton(onClick = { onValueChange(value - step) }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrementar",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}