package com.calleserpis.overtime.domain.repository

import com.calleserpis.overtime.domain.model.Overtime
import kotlinx.coroutines.flow.Flow

interface OvertimeRepository {

    fun getOvertimeEntryById(id: Int): Flow<Overtime?>

    fun getOvertimeEntries(): Flow<List<Overtime>>

    //TODO obtener entradas de un día en concreto

    suspend fun deleteOvertimeEntry(id: Int?)

    suspend fun insertOvertimeEntry(overtimeEntry: Overtime)


}