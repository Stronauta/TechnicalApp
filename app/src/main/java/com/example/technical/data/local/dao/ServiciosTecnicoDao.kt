package com.example.technical.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import com.example.technical.data.local.entities.ServiciosTecnicoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiciosTecnicoDao {
    @Upsert()
    suspend fun save(serviciosTecnicoEntity: ServiciosTecnicoEntity)

    @Query(
        """
        SELECT * 
        FROM ServiciosTecnicos
        WHERE ServicioId= :id
        LIMIT 1
        """
    )

    suspend fun find(id: Int): ServiciosTecnicoEntity?

    @Delete
    suspend fun deleteService(serviciosTecnicoEntity: ServiciosTecnicoEntity)

    @Query("SELECT * FROM ServiciosTecnicos")
    fun findAll(): Flow<List<ServiciosTecnicoEntity>>
}