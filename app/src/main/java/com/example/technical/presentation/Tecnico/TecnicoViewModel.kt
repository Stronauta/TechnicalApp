package com.example.technical.presentation.Tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.repository.TechnicalRepository
import com.example.technical.data.repository.TiposRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TecnicoViewModel(
    private val repository: TechnicalRepository,
    private val tecnicoid: Int,
    private val TipoRepository: TiposRepository
) : ViewModel() {

    var uiState = MutableStateFlow(TecnicoUiState())
        private set

    val technicals = repository.getTecnicos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val tipoTecnico = TipoRepository.getTipos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onNameChange(name: String){
        uiState.update {
            it.copy(nombreTecnico = name)
        }
    }

    fun onSalaryChange(salary: Double){
        uiState.update {
            it.copy(salarioTecnico = salary)
        }
    }

    fun onTipoChange(tipo: String){
        uiState.update {
            it.copy(tipoTecnico = tipo)
        }
    }

    init {
        viewModelScope.launch {
            val technical = repository.getTecnicoById(tecnicoid)

            technical?.let {
                uiState.update {
                    it.copy(
                        tenicoId = technical.tecnicoId ?: 0,
                        nombreTecnico = technical.tecnicoName,
                        salarioTecnico = technical.monto,
                        tipoTecnico = technical.tipo
                    )
                }
            }

        }
    }

    fun saveTechnical(technical: TechnicalEntity){
        viewModelScope.launch {
            repository.saveTecnico(technical)
        }
    }

    fun deleteTechnical(technical: TechnicalEntity){
        viewModelScope.launch {
            repository.deleteTecnico(technical)
        }
    }


    data class TecnicoUiState(
        val tenicoId: Int = 0,
        var nombreTecnico: String = "",
        var errorName: String? = "",
        var salarioTecnico: Double = 0.0,
        var errorSalary: Double? = 0.0,
        var error: String? = null,
        var tipoTecnico: String = "",
        var errorTipo: String? = null

    )

    fun TecnicoUiState.toEntity() : TechnicalEntity{
        return TechnicalEntity(
            tecnicoId = tenicoId,
            tecnicoName = nombreTecnico,
            monto = salarioTecnico,
            tipo = tipoTecnico
        )
    }
}