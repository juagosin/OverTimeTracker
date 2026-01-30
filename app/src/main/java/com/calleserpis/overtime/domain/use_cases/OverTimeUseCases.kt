package com.calleserpis.overtime.domain.use_cases

data class OverTimeUseCases (
    val addOvertimeUseCase: AddOvertimeUseCase,
    val getOvertimeEntriesByMonthUseCase: GetOvertimeEntriesByMonthUseCase
)