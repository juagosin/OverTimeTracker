package com.calleserpis.overtime.data.repository

import com.calleserpis.overtime.data.local.OvertimeDao
import com.calleserpis.overtime.data.mapper.toDomain
import com.calleserpis.overtime.data.mapper.toEntity
import com.calleserpis.overtime.data.preferences.OvertimePreferencesManager
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.repository.OvertimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OvertimeRepositoryImpl(
    private val overtimeDao: OvertimeDao,
    private val overtimePreferencesManager: OvertimePreferencesManager
): OvertimeRepository {
    override fun getOvertimeEntryById(id: Int): Flow<Overtime?> {
        return overtimeDao.getOvertimeEntry(id).map { it?.toDomain() }
    }

    override fun getOvertimeEntries(): Flow<List<Overtime>> {
        return overtimeDao.getOvertimeEntries().map { it.toDomain() }
    }

    override suspend fun deleteOvertimeEntry(id: Int?) {
        overtimeDao.deleteOvertimeEntry(id!!)
    }

    override suspend fun insertOvertimeEntry(overtimeEntry: Overtime) {
        overtimeDao.insertOvertimeEntry(overtimeEntry.toEntity())

    }
}