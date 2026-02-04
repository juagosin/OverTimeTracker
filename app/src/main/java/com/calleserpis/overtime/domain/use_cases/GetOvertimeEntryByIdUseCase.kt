package com.calleserpis.overtime.domain.use_cases

import com.calleserpis.overtime.domain.repository.OvertimeRepository

class GetOvertimeEntryByIdUseCase (private val overtimeRepository: OvertimeRepository) {
    operator fun invoke(id: Int) = overtimeRepository.getOvertimeEntryById(id)

}