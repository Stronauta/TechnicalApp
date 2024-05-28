package com.example.technical.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ServiciosTecnicos")
data class ServiciosTecnicoEntity(
    @PrimaryKey
    val ServicioId: Int? = null,
    var Fecha: String = "",
    var TecnicoId: Int? = null,
    var Cliente: String = "",
    var Descripcion: String = "",
    var Total: Double = 0.0
)

// ServicioId, Fecha, TecnicoId, Cliente, Descripcion, Total