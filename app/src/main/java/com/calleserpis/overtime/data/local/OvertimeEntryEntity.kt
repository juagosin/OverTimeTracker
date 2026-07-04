package com.calleserpis.overtime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "overtime_entry")
data class OvertimeEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val empresa: String,
    val dateIni: Long,
    val dateFin: Long,
    val categoria: String,
    val detalles: String,
    val dinero: Double = 0.0,

)
