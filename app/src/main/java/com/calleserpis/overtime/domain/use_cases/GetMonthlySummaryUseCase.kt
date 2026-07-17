package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.model.PeriodSummary
import com.calleserpis.overtime.domain.model.buildMonthlySummary
import com.calleserpis.overtime.domain.model.monthlyRangeMillis
import com.calleserpis.overtime.domain.repository.OvertimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetMonthlySummaryUseCase(
    private val overtimeRepository: OvertimeRepository
) {
    operator fun invoke(referenceDate: LocalDate): Flow<PeriodSummary> {
        val (startDate, endDate) = monthlyRangeMillis(referenceDate)
        return overtimeRepository.getOvertimeEntriesByRange(startDate, endDate)
            .map { buildMonthlySummary(referenceDate, it) }
    }
}
