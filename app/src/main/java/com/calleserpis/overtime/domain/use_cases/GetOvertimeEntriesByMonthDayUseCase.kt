package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.repository.OvertimeRepository
import kotlinx.coroutines.flow.Flow

class GetOvertimeEntriesByMonthDayUseCase (private val overtimeRepository: OvertimeRepository) {
    operator fun invoke(yearMonthDay: String): Flow<List<Overtime>> {
        return overtimeRepository.getOvertimeEntriesByMonthDay(yearMonthDay)
    }
}