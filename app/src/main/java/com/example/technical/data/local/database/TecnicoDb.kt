package com.example.technical.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.local.dao.TecnicoDao

@Database(
    entities = [
        TechnicalEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class TecnicoDatabase : RoomDatabase(){
    abstract fun technicalDao(): TecnicoDao
}

