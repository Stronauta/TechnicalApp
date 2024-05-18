package com.example.technical.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import com.example.technical.data.local.entities.TechnicalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TecnicoDao {
    @Upsert()
    suspend fun save(technicalEntity: TechnicalEntity)

    @Query(
        """
        SELECT * 
        FROM Tecnico
        WHERE tecnicoId= :id
        LIMIT 1
        """
    )

    suspend fun find(id: Int): TechnicalEntity?

    @Delete
    suspend fun delete(technicalEntity: TechnicalEntity)

    @Query("SELECT * FROM Tecnico")
    fun findAll(): Flow<List<TechnicalEntity>>


}
