package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.repository.OvertimeRepository

class DeleteOvertimeUseCase (
    private val overtimeRepository: OvertimeRepository
) {
    suspend operator fun invoke(id: Int?){
        overtimeRepository.deleteOvertimeEntry(id)

    }
}