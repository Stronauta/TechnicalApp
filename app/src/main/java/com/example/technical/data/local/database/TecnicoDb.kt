package com.example.technical.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.data.local.dao.TecnicoDao
import com.example.technical.data.local.dao.TiposTecnicosDao

@Database(
    entities = [
        TechnicalEntity::class,
        TiposEntity::class
    ],
    version = 3,
    exportSchema = false
)

abstract class TecnicoDatabase : RoomDatabase(){
    abstract fun technicalDao(): TecnicoDao
    abstract fun tiposDao(): TiposTecnicosDao

}

