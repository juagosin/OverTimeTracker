package com.calleserpis.overtime.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calleserpis.overtime.data.local.timeDifference
import com.calleserpis.overtime.data.local.toDayOfMonth
import com.calleserpis.overtime.data.local.toHourMinute
import com.calleserpis.overtime.data.local.toMonthShortName
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.ui.theme.cobrada
import com.calleserpis.overtime.ui.theme.noCobrada

@Composable
fun OverTimeListItem(onNavigateToDetail: (Long?) -> Unit, entry: Overtime) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
        ,
        onClick = {
            onNavigateToDetail(entry.id.toLong())
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //BLOQUE Fecha
            Column(
                modifier = Modifier
                    .background(
                        color = colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LUN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium

                )
                Text(
                    text = entry.dateIni.toDayOfMonth().toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = entry.dateIni.toMonthShortName(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            //Bloque EMPRESA - HORA - CATEGORIA
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = entry.empresa,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = entry.dateIni.toHourMinute()+" - "+entry.dateFin.toHourMinute(),
                    fontSize = 14.sp,
                    )
                Row(verticalAlignment = Alignment.CenterVertically){
                    var textOvertime = "Pendiente"
                    var colorOvertime = noCobrada
                    if(entry.categoria.toString() == "COBRADA"){
                        textOvertime = "Cobrada"
                        colorOvertime = cobrada
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = textOvertime,
                        tint = colorOvertime,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Cobrada",
                        fontSize = 13.sp,
                        color = colorOvertime,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = timeDifference(entry.dateIni, entry.dateFin),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,

                )
        }
    }

}