package com.calleserpis.overtime.ui.screens.summary

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.overtime.R
import com.calleserpis.overtime.domain.model.PeriodSummary
import com.calleserpis.overtime.domain.model.SummaryPeriodType
import com.calleserpis.overtime.domain.model.toDurationLabel
import com.calleserpis.overtime.domain.model.toMoneyLabel
import com.calleserpis.overtime.ui.screens.list.OverTimeListItem

@Composable
fun SummaryScreen(
    onNavigateToDetail: (Long?) -> Unit,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SummaryPeriodSelector(
            selectedPeriodType = state.selectedPeriodType,
            onPeriodTypeSelected = { viewModel.onEvent(SummaryEvent.OnPeriodTypeChanged(it)) }
        )
        SummaryHeader(
            summary = state.summary,
            onPrevious = { viewModel.onEvent(SummaryEvent.OnPreviousPeriod) },
            onNext = { viewModel.onEvent(SummaryEvent.OnNextPeriod) }
        )
        SummaryMetrics(summary = state.summary)
        SummaryActions(
            onExport = {
                val file = SummaryPdfExporter.export(context, state.summary)
                if (file == null || !SummaryPdfExporter.openExportedFile(context, file)) {
                    Toast.makeText(context, R.string.summary_export_error, Toast.LENGTH_SHORT).show()
                }
            },
            onShare = {
                val file = SummaryPdfExporter.export(context, state.summary)
                if (file == null || !SummaryPdfExporter.share(context, state.summary, file)) {
                    Toast.makeText(context, R.string.summary_share_error, Toast.LENGTH_SHORT).show()
                }
            }
        )

        if (state.summary.entries.isEmpty()) {
            SummaryEmptyState()
        } else {
            Text(
                text = stringResource(R.string.summary_entries_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 12.dp)
            )
            state.summary.entries.forEach { entry ->
                OverTimeListItem(onNavigateToDetail = onNavigateToDetail, entry = entry)
            }
        }
    }
}

@Composable
private fun SummaryPeriodSelector(
    selectedPeriodType: SummaryPeriodType,
    onPeriodTypeSelected: (SummaryPeriodType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryToggleButton(
            text = stringResource(R.string.summary_period_week),
            selected = selectedPeriodType == SummaryPeriodType.WEEK,
            onClick = { onPeriodTypeSelected(SummaryPeriodType.WEEK) },
            modifier = Modifier.weight(1f)
        )
        SummaryToggleButton(
            text = stringResource(R.string.summary_period_month),
            selected = selectedPeriodType == SummaryPeriodType.MONTH,
            onClick = { onPeriodTypeSelected(SummaryPeriodType.MONTH) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainerHigh
            }
        )
    ) {
        Text(text)
    }
}

@Composable
private fun SummaryHeader(
    summary: PeriodSummary,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onPrevious) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                    contentDescription = stringResource(R.string.summary_previous_period)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = summary.periodLabel,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = summary.periodDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
            IconButton(onClick = onNext) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                    contentDescription = stringResource(R.string.summary_next_period)
                )
            }
        }
    }
}

@Composable
private fun SummaryMetrics(summary: PeriodSummary) {
    Column(modifier = Modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SummaryMetricCard(
                title = stringResource(R.string.summary_total_hours),
                value = summary.totalDurationMinutes.toDurationLabel(),
                modifier = Modifier.weight(1f)
            )
            SummaryMetricCard(
                title = stringResource(R.string.summary_total_money),
                value = summary.totalMoney.toMoneyLabel(),
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SummaryMetricCard(
                title = stringResource(R.string.summary_total_entries),
                value = summary.totalEntries.toString(),
                modifier = Modifier.weight(1f)
            )
            SummaryMetricCard(
                title = stringResource(R.string.summary_pending_hours),
                value = summary.pendingDurationMinutes.toDurationLabel(),
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SummaryMetricCard(
                title = stringResource(R.string.summary_pending_money),
                value = summary.pendingMoney.toMoneyLabel(),
                modifier = Modifier.weight(1f)
            )
            SummaryMetricCard(
                title = stringResource(R.string.summary_paid_hours),
                value = summary.paidDurationMinutes.toDurationLabel(),
                modifier = Modifier.weight(1f)
            )
        }
        SummaryMetricCard(
            title = stringResource(R.string.summary_paid_money),
            value = summary.paidMoney.toMoneyLabel(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SummaryMetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun SummaryActions(
    onExport: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(onClick = onExport, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.PictureAsPdf,
                contentDescription = stringResource(R.string.summary_export_pdf)
            )
            Text(
                text = stringResource(R.string.summary_export_pdf),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        OutlinedButton(onClick = onShare, modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.IosShare,
                contentDescription = stringResource(R.string.summary_share)
            )
            Text(
                text = stringResource(R.string.summary_share),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun SummaryEmptyState() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.summary_empty),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.summary_empty_hint),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
