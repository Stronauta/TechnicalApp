package com.example.technical.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tecnico")
data class TechnicalEntity(
    @PrimaryKey
    val tecnicoId: Int? = null,
    var tecnicoName : String = "",
    var monto: Double = 0.0,
    var tipo: String = "",
)