package com.calleserpis.overtime.domain.use_cases

data class OverTimeUseCases (
    val addOvertimeUseCase: AddOvertimeUseCase,
    val getOvertimeEntriesByMonthUseCase: GetOvertimeEntriesByMonthUseCase,
    val getOvertimeEntriesByMonthDayUseCase: GetOvertimeEntriesByMonthDayUseCase,
    val getWeeklySummaryUseCase: GetWeeklySummaryUseCase,
    val getMonthlySummaryUseCase: GetMonthlySummaryUseCase,
    val getOvertimeEntriesUseCase: GetOvertimeEntriesUseCase,
    val getOvertimeEntryByIdUseCase: GetOvertimeEntryByIdUseCase,
    val deleteOvertimeUseCase: DeleteOvertimeUseCase


)
