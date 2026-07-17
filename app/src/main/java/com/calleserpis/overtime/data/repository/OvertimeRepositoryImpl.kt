package com.calleserpis.overtime.data.repository

import android.util.Log
import com.calleserpis.overtime.data.local.OvertimeDao
import com.calleserpis.overtime.data.local.toMonthDayRange
import com.calleserpis.overtime.data.local.toMonthRange
import com.calleserpis.overtime.data.mapper.toDomain
import com.calleserpis.overtime.data.mapper.toEntity
import com.calleserpis.overtime.data.preferences.OvertimePreferencesManager
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.repository.OvertimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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

    override fun getOvertimeEntriesByRange(startDate: Long, endDate: Long): Flow<List<Overtime>> {
        return overtimeDao.getOvertimeEntriesByRange(startDate, endDate).map { it.toDomain() }
    }

    override fun getOvertimeEntriesByMonth(yearMonth: String): Flow<List<Overtime>> {
        val (startDate, endDate) = yearMonth.toMonthRange()

        return overtimeDao.getOvertimeEntriesByMonth(startDate, endDate).onEach {
                entities ->
            entities.forEach { _ ->
            }
        }.map { it.toDomain() }
    }


    override fun getOvertimeEntriesByMonthDay(yearMonthDay: String): Flow<List<Overtime>> {
        val (startDate, endDate) = yearMonthDay.toMonthDayRange()

        return overtimeDao.getOvertimeEntriesByMonthDay(startDate, endDate).onEach {
                entities ->

            entities.forEach { _ ->
            }
        }.map { it.toDomain() }
    }

    override suspend fun deleteOvertimeEntry(id: Int?) {
        overtimeDao.deleteOvertimeEntry(id!!)
    }

    override suspend fun insertOvertimeEntry(overtimeEntry: Overtime) {
        overtimeDao.insertOvertimeEntry(overtimeEntry.toEntity())

    }
}
