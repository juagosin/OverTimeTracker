package com.calleserpis.overtime.data.mapper

import com.calleserpis.overtime.data.local.OvertimeEntryEntity
import com.calleserpis.overtime.domain.model.Overtime
import com.calleserpis.overtime.domain.model.OvertimeCategory

fun OvertimeEntryEntity.toDomain() : Overtime{
    return  Overtime (
        id = id,
        empresa = empresa,
        dateIni = dateIni,
        dateFin = dateFin,
        categoria = OvertimeCategory.fromString(categoria),
        detalles = detalles
    )
}

fun Overtime.toEntity() : OvertimeEntryEntity{
    return  OvertimeEntryEntity (
        id = id,
        empresa = empresa,
        dateIni = dateIni,
        dateFin = dateFin,
        categoria = categoria.value,
        detalles = detalles
    )
}

fun List<OvertimeEntryEntity>.toDomain() : List<Overtime>{
    return map { it.toDomain() }
}