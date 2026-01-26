package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.repository.OvertimeRepository

class AddOvertimeUseCase(
    private val overtimeRepository: OvertimeRepository

) {
    suspend operator fun invoke(overtime: Overtime) =
        overtimeRepository.insertOvertimeEntry((overtime))
}


