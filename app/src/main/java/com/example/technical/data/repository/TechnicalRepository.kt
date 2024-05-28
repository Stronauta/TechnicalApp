package com.example.technical.data.repository

import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.local.dao.TecnicoDao


class TechnicalRepository(private val technicalDao: TecnicoDao) {
    suspend fun saveTecnico(tecnico: TechnicalEntity) = technicalDao.save(tecnico)

    fun getTecnicos() = technicalDao.findAll()

    suspend fun getTecnicoById(tecnicoId: Int) = technicalDao.find(tecnicoId)

    suspend fun deleteTecnico(tecnico: TechnicalEntity) = technicalDao.delete(tecnico)

    suspend fun getTecnicoByName(name: String, tecnicoId: Int) = technicalDao.findNameTecnico(name, tecnicoId)
}