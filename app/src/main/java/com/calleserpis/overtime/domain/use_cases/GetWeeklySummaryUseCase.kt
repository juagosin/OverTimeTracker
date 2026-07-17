package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.model.PeriodSummary
import com.calleserpis.overtime.domain.model.buildWeeklySummary
import com.calleserpis.overtime.domain.model.weeklyRangeMillis
import com.calleserpis.overtime.domain.repository.OvertimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetWeeklySummaryUseCase(
    private val overtimeRepository: OvertimeRepository
) {
    operator fun invoke(referenceDate: LocalDate): Flow<PeriodSummary> {
        val (startDate, endDate) = weeklyRangeMillis(referenceDate)
        return overtimeRepository.getOvertimeEntriesByRange(startDate, endDate)
            .map { buildWeeklySummary(referenceDate, it) }
    }
}
