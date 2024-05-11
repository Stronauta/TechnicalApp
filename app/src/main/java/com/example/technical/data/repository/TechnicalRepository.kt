package com.example.technical.data.repository

import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.local.dao.TecnicoDao


class TechnicalRepository(private val technicalDao: TecnicoDao) {
    suspend fun saveTecnico(tecnico: TechnicalEntity) = technicalDao.save(tecnico)

    fun getTecnico() = technicalDao.findAll()

}