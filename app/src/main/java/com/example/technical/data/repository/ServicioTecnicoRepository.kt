package com.example.technical.data.repository

import com.example.technical.data.local.entities.ServiciosTecnicoEntity
import com.example.technical.data.local.dao.ServiciosTecnicoDao

class ServicioTecnicoRepository(
    private val serviciosTecnicoDao: ServiciosTecnicoDao
) {
    suspend fun saveServicio(servicio: ServiciosTecnicoEntity) = serviciosTecnicoDao.save(servicio)

    fun getServicios() = serviciosTecnicoDao.findAll()

    suspend fun getServicioById(ServicioId: Int) = serviciosTecnicoDao.find(ServicioId)

    suspend fun deleteServicio(servicio: ServiciosTecnicoEntity) = serviciosTecnicoDao.deleteService(servicio)



}