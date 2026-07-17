package com.calleserpis.overtime.ui.screens.summary

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.calleserpis.overtime.R
import com.calleserpis.overtime.data.local.toHourMinute
import com.calleserpis.overtime.data.local.toMonthShortName
import com.calleserpis.overtime.domain.model.PeriodSummary
import com.calleserpis.overtime.domain.model.durationMinutes
import com.calleserpis.overtime.domain.model.toDurationLabel
import com.calleserpis.overtime.domain.model.toMoneyLabel
import java.io.File
import java.io.FileOutputStream

object SummaryPdfExporter {

    fun export(context: Context, summary: PeriodSummary): File? {
        val directory = context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOCUMENTS)
            ?: context.filesDir
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, buildFileName(summary))
        createPdf(file, context, summary)
        return file
    }

    fun openExportedFile(context: Context, file: File): Boolean {
        val uri = file.toContentUri(context)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        return try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.summary_export_pdf)))
            true
        } catch (_: ActivityNotFoundException) {
            false
        }
    }

    fun share(context: Context, summary: PeriodSummary, file: File): Boolean {
        val uri = file.toContentUri(context)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, buildShareText(context, summary))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        return try {
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.summary_share)))
            true
        } catch (_: ActivityNotFoundException) {
            false
        }
    }

    fun buildShareText(context: Context, summary: PeriodSummary): String {
        return context.getString(
            R.string.summary_share_text,
            summary.periodLabel,
            summary.totalEntries,
            summary.totalDurationMinutes.toDurationLabel(),
            summary.totalMoney.toMoneyLabel(),
            summary.pendingDurationMinutes.toDurationLabel(),
            summary.pendingMoney.toMoneyLabel(),
            summary.paidDurationMinutes.toDurationLabel(),
            summary.paidMoney.toMoneyLabel()
        )
    }

    private fun createPdf(file: File, context: Context, summary: PeriodSummary) {
        val document = PdfDocument()
        val titlePaint = Paint().apply {
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val subtitlePaint = Paint().apply { textSize = 12f }
        val sectionPaint = Paint().apply {
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val bodyPaint = Paint().apply { textSize = 11f }
        val lineHeight = 18f
        val pageWidth = 595
        val pageHeight = 842
        var pageNumber = 1
        var page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
        var canvas = page.canvas
        var currentY = 40f

        fun startNewPage() {
            document.finishPage(page)
            pageNumber += 1
            page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
            canvas = page.canvas
            currentY = 40f
        }

        fun ensureSpace(lines: Int = 1) {
            if (currentY + (lineHeight * lines) > pageHeight - 40) {
                startNewPage()
            }
        }

        fun drawLine(text: String, paint: Paint = bodyPaint) {
            ensureSpace()
            canvas.drawText(text, 40f, currentY, paint)
            currentY += lineHeight
        }

        drawLine(context.getString(R.string.summary_pdf_title), titlePaint)
        drawLine(summary.periodLabel, sectionPaint)
        drawLine(summary.periodDescription, subtitlePaint)
        currentY += 10f

        drawLine(context.getString(R.string.summary_pdf_metrics), sectionPaint)
        drawLine(context.getString(R.string.summary_total_entries_value, summary.totalEntries))
        drawLine(context.getString(R.string.summary_total_hours_value, summary.totalDurationMinutes.toDurationLabel()))
        drawLine(context.getString(R.string.summary_total_money_value, summary.totalMoney.toMoneyLabel()))
        drawLine(context.getString(R.string.summary_pending_hours_value, summary.pendingDurationMinutes.toDurationLabel()))
        drawLine(context.getString(R.string.summary_pending_money_value, summary.pendingMoney.toMoneyLabel()))
        drawLine(context.getString(R.string.summary_paid_hours_value, summary.paidDurationMinutes.toDurationLabel()))
        drawLine(context.getString(R.string.summary_paid_money_value, summary.paidMoney.toMoneyLabel()))
        currentY += 10f

        drawLine(context.getString(R.string.summary_entries_title), sectionPaint)
        if (summary.entries.isEmpty()) {
            drawLine(context.getString(R.string.summary_empty))
        } else {
            summary.entries.forEach { entry ->
                ensureSpace(3)
                drawLine(
                    "${entry.dateIni.toMonthShortName()} ${entry.dateIni.toHourMinute()} - ${entry.dateFin.toHourMinute()} | ${entry.durationMinutes().toDurationLabel()}"
                )
                drawLine(entry.empresa)
                drawLine("${entry.categoria.value} | ${entry.dinero.toMoneyLabel()}")
                currentY += 6f
            }
        }

        document.finishPage(page)
        FileOutputStream(file).use { output ->
            document.writeTo(output)
        }
        document.close()
    }

    private fun buildFileName(summary: PeriodSummary): String {
        val type = summary.periodType.name.lowercase()
        val label = summary.periodLabel
            .replace(" ", "_")
            .replace("/", "-")
            .replace(":", "-")
        return "resumen_${type}_${label}.pdf"
    }

    private fun File.toContentUri(context: Context) = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        this
    )
}
