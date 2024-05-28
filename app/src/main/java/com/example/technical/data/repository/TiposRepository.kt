package com.example.technical.data.repository

import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.data.local.dao.TiposTecnicosDao

class TiposRepository(private val tiposTecnicosDao: TiposTecnicosDao) {

    suspend fun saveTipo(tipo: TiposEntity) = tiposTecnicosDao.save(tipo)

    fun getTipos() = tiposTecnicosDao.findAll()

    suspend fun getTipoById(id: Int) = tiposTecnicosDao.find(id)

    suspend fun getDescripcionTipo(descripcion: String, tipoTecnicoId: Int) = tiposTecnicosDao.findByDescripcion(descripcion.toLowerCase().replace(" ", ""), tipoTecnicoId)

    suspend fun deletecTipo(tipo: TiposEntity) = tiposTecnicosDao.delete(tipo)

    suspend fun getTipoTecnico(descripcion: String) = tiposTecnicosDao.findDescripcionTecnico(descripcion)

}