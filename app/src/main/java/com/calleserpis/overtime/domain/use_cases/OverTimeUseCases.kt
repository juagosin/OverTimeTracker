package com.calleserpis.overtime.domain.use_cases

data class OverTimeUseCases (
    val addOvertimeUseCase: AddOvertimeUseCase,
    val getOvertimeEntriesByMonthUseCase: GetOvertimeEntriesByMonthUseCase,
    val getOvertimeEntriesByMonthDayUseCase: GetOvertimeEntriesByMonthDayUseCase,
    val getOvertimeEntriesUseCase: GetOvertimeEntriesUseCase,
    val getOvertimeEntryByIdUseCase: GetOvertimeEntryByIdUseCase

)