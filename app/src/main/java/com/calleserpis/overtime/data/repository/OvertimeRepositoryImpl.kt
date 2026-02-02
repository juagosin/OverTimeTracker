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
import java.util.Date

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

    override fun getOvertimeEntriesByMonth(yearMonth: String): Flow<List<Overtime>> {
        val (startDate, endDate) = yearMonth.toMonthRange()
        Log.d("OvertimeRepo", "=== Query Debug ===")
        Log.d("OvertimeRepo", "Input yearMonth: $yearMonth")
        Log.d("OvertimeRepo", "startDate (Long): $startDate")
        Log.d("OvertimeRepo", "endDate (Long): $endDate")
        Log.d("OvertimeRepo", "startDate (Date): ${Date(startDate)}")
        Log.d("OvertimeRepo", "endDate (Date): ${Date(endDate)}")
        return overtimeDao.getOvertimeEntriesByMonth(startDate, endDate).onEach {
                entities ->
            Log.d("OvertimeRepo", "Raw entities from DB: ${entities.size}")
            entities.forEach { entity ->
                Log.d("OvertimeRepo", "Entity - ID: ${entity.id}, dateIni: ${entity.dateIni} (${Date(entity.dateIni)})")
            }
        }.map { it.toDomain() }
    }


    override fun getOvertimeEntriesByMonthDay(yearMonthDay: String): Flow<List<Overtime>> {
        val (startDate, endDate) = yearMonthDay.toMonthDayRange()
        Log.d("OvertimeRepo", "=== Query Debug ===")
        Log.d("OvertimeRepo", "Input yearMonth: $yearMonthDay")
        Log.d("OvertimeRepo", "startDate (Long): $startDate")
        Log.d("OvertimeRepo", "endDate (Long): $endDate")
        Log.d("OvertimeRepo", "startDate (Date): ${Date(startDate)}")
        Log.d("OvertimeRepo", "endDate (Date): ${Date(endDate)}")
        return overtimeDao.getOvertimeEntriesByMonthDay(startDate, endDate).onEach {
                entities ->
            Log.d("OvertimeRepo", "Raw entities from DB: ${entities.size}")
            entities.forEach { entity ->
                Log.d("OvertimeRepo", "Entity - ID: ${entity.id}, dateIni: ${entity.dateIni} (${Date(entity.dateIni)})")
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