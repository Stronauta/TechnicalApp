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

    @Delete
    suspend fun delete(tiposTecnico: TiposEntity)

    @Query("SELECT * FROM TiposTecnicos")
    fun findAll(): Flow<List<TiposEntity>>

    @Query(
        "" +
                "SELECT * " +
                "FROM TiposTecnicos " +
                "WHERE Descripción " +
                "LIKE :descripcion " +
                "AND tipoId = :tipoTecnicoId"
    )
    suspend fun findByDescripcion(descripcion: String, tipoTecnicoId: Int): TiposEntity?

    @Query(
        """
        SELECT TipoId 
        FROM TiposTecnicos
        WHERE Descripción = :descripcion
        LIMIT 1
        """
    )
    suspend fun findDescripcionTecnico(descripcion: String): Int?

}

