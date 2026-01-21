package com.calleserpis.overtime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OvertimeEntryEntity::class], version = 1, exportSchema = false)
abstract class OvertimeDatabase: RoomDatabase() {
    abstract val dao: OvertimeDao
    companion object {
        const val DATABASE_NAME = "overtime_db"
    }
}


