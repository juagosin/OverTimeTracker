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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo

@Composable
fun DetailScreen() {
    var selectedOption by remember { mutableStateOf("Cobrada") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Empresa") },
            modifier = Modifier.fillMaxWidth()

        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()

        )
        //spinners de hora ini y hora fin
        Spacer(modifier = Modifier.height(4.dp))
        SegmentedSwitch(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                 containerColor = colorScheme.primary,
                 contentColor = colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}

@Composable
fun SegmentedSwitch(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    option1: String = "Cobrada",
    option2: String = "No Cobrada",
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

        Spacer(modifier = Modifier.width(4.dp))

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
    Box(
        modifier = modifier

            .background(
                if (isSelected) colorScheme.primary
                else Color.Transparent
            )
            //.clip(RoundedCornerShape(6.dp))
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