package com.example.technical.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TiposTecnicos")
data class TiposEntity (
    @PrimaryKey
    val TipoId: Int? = null,
    val Descripción: String = ""
)
