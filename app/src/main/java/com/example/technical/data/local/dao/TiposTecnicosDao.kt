package com.example.technical.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.technical.data.local.entities.TechnicalEntity

import com.example.technical.data.local.entities.TiposEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TiposTecnicosDao {
    @Upsert
    suspend fun save(tiposEntity: TiposEntity)

    @Query(
        """
        SELECT * 
        FROM TiposTecnicos
        WHERE TipoId = :id
        LIMIT 1
        """
    )
    suspend fun find(id: Int): TiposEntity?

    @Query("SELECT * FROM TiposTecnicos")
    fun findAll(): Flow<List<TiposEntity>>



}

