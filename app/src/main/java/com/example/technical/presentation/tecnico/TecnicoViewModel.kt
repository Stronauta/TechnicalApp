package com.example.technical.presentation.tecnico

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


    val regexName: Regex = Regex("^[a-zA-Z]+(?: [a-zA-Z]+)*$")
    val regexSalary: Regex = Regex("^[0-9]{0,7}(\\.[0-9]{0,2})?$")


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
                        tecnicoId = technical.tecnicoId ?: 0,
                        nombreTecnico = technical.tecnicoName,
                        salarioTecnico = technical.monto,
                    )
                }
            }

        }
    }


    fun saveTechnical(){
        var isValid = true

        uiState.update {
            it.copy(
                errorName = null,
                errorSalary = null,
                errorTipo = null
            )
        }

        if(uiState.value.tecnicoId == 0){
            uiState.update {
                it.copy(tecnicoId = null)
            }
        }

        if(uiState.value.nombreTecnico.isEmpty()){
            uiState.update {
                it.copy(errorName = "Campo requerido")
            }
            isValid = false
        }

        if(uiState.value.salarioTecnico == 0.0) {
            uiState.update {
                it.copy(errorSalary = "Campo requerido")
            }
            isValid = false
        }

        if(uiState.value.tipoTecnico.isNullOrEmpty()) {
            uiState.update {
                it.copy(errorTipo = "Campo requerido")
            }
            isValid = false
        }

        viewModelScope.launch {
            if(!validateName()){
                uiState.update {
                    it.copy(errorName = "Nombre ya existe")
                }
                isValid = false
            }

            if(!isValid){
                return@launch
            }

            uiState.update {
                it.copy(isSaving = true)
            }
            val tipoId = TipoRepository.getTipoTecnico(uiState.value.tipoTecnico?:"")

            if(uiState.value.errorSalary == null || uiState.value.errorName == null || uiState.value.errorTipo == null){
                repository.saveTecnico(uiState.value.toEntity(tipoId))
            }
        }

    }

    fun deleteTechnical(){
        viewModelScope.launch {
            repository.deleteTecnico(uiState.value.toEntity())
        }
    }

    fun newTechnical(){
        viewModelScope.launch {
            uiState.value = TecnicoUiState()
        }

    }

    suspend fun validateName(): Boolean{
        val name = repository.getTecnicoByName(uiState.value.nombreTecnico, uiState.value.tecnicoId ?:  0)
        return name == null
    }


    data class TecnicoUiState(
        val tecnicoId: Int? = null,
        var nombreTecnico: String = "",
        var errorName: String? = null,
        var salarioTecnico: Double = 0.0,
        var errorSalary: String? = null,
        var tipoTecnico: String = "",
        var errorTipo: String? = null,
        var isSaving: Boolean = false

    )

    fun TecnicoUiState.toEntity(tipoId: Int? = null) : TechnicalEntity{
        return TechnicalEntity(
            tecnicoId = tecnicoId,
            tecnicoName = nombreTecnico,
            monto = salarioTecnico ?: 0.0,
            tipo = tipoId
        )
    }
}