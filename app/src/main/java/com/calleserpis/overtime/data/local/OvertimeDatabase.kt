package com.calleserpis.overtime.data.local

import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [OvertimeEntryEntity::class], version = 2, exportSchema = false)
abstract class OvertimeDatabase: RoomDatabase() {
    abstract val dao: OvertimeDao
    companion object {
        const val DATABASE_NAME = "overtime_db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE overtime_entry ADD COLUMN dinero REAL NOT NULL DEFAULT 0.0"
                )
            }
        }
    }
}


