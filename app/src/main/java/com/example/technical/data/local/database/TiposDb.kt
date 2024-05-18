package com.example.technical.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technical.data.local.dao.TiposTecnicosDao
import com.example.technical.data.local.entities.TiposEntity

@Database(
    entities = [
        TiposEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class tiposTecnicoDatabase : RoomDatabase(){
    abstract fun tiposDao(): TiposTecnicosDao
}