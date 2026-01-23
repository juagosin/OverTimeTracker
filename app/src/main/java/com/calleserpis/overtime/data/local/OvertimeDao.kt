package com.calleserpis.overtime.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OvertimeDao {
    @Query("SELECT * FROM overtime_entry where id = :id")
    fun getOvertimeEntry(id: Int): Flow<OvertimeEntryEntity>

    @Query("SELECT * FROM overtime_entry order by dateIni desc")
    fun getOvertimeEntries(): Flow<List<OvertimeEntryEntity>>

    @Query("DELETE FROM overtime_entry where id = :id")
    suspend fun deleteOvertimeEntry(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOvertimeEntry(overtimeEntryEntity: OvertimeEntryEntity)

}