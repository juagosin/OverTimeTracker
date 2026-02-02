package com.calleserpis.overtime.domain.repository

import com.calleserpis.overtime.domain.model.Overtime
import kotlinx.coroutines.flow.Flow

interface OvertimeRepository {

    fun getOvertimeEntryById(id: Int): Flow<Overtime?>

    fun getOvertimeEntries(): Flow<List<Overtime>>

    fun getOvertimeEntriesByMonth(yearMonth: String): Flow<List<Overtime>>
    fun getOvertimeEntriesByMonthDay(yearMonthDay: String): Flow<List<Overtime>>


    suspend fun deleteOvertimeEntry(id: Int?)

    suspend fun insertOvertimeEntry(overtimeEntry: Overtime)


}