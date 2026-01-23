package com.calleserpis.overtime.domain.model

data class Overtime (
    val id: Int,
    val empresa: String,
    val dateIni: Long,
    val dateFin: Long,
    val categoria: OvertimeCategory,
    val detalles: String
)

enum class OvertimeCategory(
    val value: String
){
    PENDIENTE("Pendiente"),
    COBRADA("Cobrada");

    companion object{
        fun fromString(value: String): OvertimeCategory{
            return entries.find { it.value == value } ?: PENDIENTE
        }
    }
}
