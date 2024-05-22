package com.example.technical.data.repository

import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.data.local.dao.TiposTecnicosDao

class TiposRepository(private val tiposTecnicosDao: TiposTecnicosDao) {

    suspend fun saveTipo(tipo: TiposEntity) = tiposTecnicosDao.save(tipo)

    fun getTipos() = tiposTecnicosDao.findAll()

    suspend fun getTipoById(id: Int) = tiposTecnicosDao.find(id)

}