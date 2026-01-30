package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.repository.OvertimeRepository
import kotlinx.coroutines.flow.Flow

class GetOvertimeEntriesByMonthUseCase (private val overtimeRepository: OvertimeRepository) {
    operator fun invoke(yearMonth: String): Flow<List<Overtime>> {
        return overtimeRepository.getOvertimeEntriesByMonth(yearMonth)
    }
}