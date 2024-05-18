package com.example.technical.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.repository.TechnicalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TecnicoViewModel(private val repository: TechnicalRepository, private val tecnicoid: Int) :
    ViewModel() {

    var uiState = MutableStateFlow(TecnicoUiState())
        private set

    val technicals = repository.getTecnicos()
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



    init {
        viewModelScope.launch {
            val technical = repository.getTecnicoById(tecnicoid)

            technical?.let {
                uiState.update {
                    it.copy(
                        tenicoId = technical.tecnicoId ?: 0,
                        nombreTecnico = technical.tecnicoName,
                        salarioTecnico = technical.monto
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
        val nombreTecnico: String = "",
        val errorName: String? = "",
        val salarioTecnico: Double = 0.0,
        val errorSalary: Double? = 0.0,
        val error: String? = null
    )

    fun TecnicoUiState.toEntity() : TechnicalEntity{
        return TechnicalEntity(
            tecnicoId = tenicoId,
            tecnicoName = nombreTecnico,
            monto = salarioTecnico
        )
    }
}