package com.example.technical.presentation.servicio_tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.repository.ServicioTecnicoRepository
import com.example.technical.data.repository.TechnicalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicioViewModel(
    private val repository: ServicioTecnicoRepository,
    private val servicioId: Int,
    private val tecnicoRepository: TechnicalRepository
) : ViewModel() {

     var uiState = MutableStateFlow(ServicioUiState())
        private set

    val Servicio = repository.getServicios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onFechaChange(fecha: String) {
        uiState.update {
            it.copy(fecha = fecha)
        }
    }

    fun onTecnicoChange(tecnicoId: String) {
        uiState.update {
            it.copy(tecnicoId = tecnicoId)
        }
    }

    fun onClienteChange(cliente: String) {
        uiState.update {
            it.copy(cliente = cliente)
        }
    }

    fun onDescripcionChange(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onTotalChange(total: Double) {
        uiState.update {
            it.copy(total = total)
        }
    }

    data class ServicioUiState(
        val servicioId: Int? = null,
        var fecha: String = "",
        var tecnicoId: String = "",
        var cliente: String = "",
        var descripcion: String = "",
        var total: Double = 0.0

    )


}