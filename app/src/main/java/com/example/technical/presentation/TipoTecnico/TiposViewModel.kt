package com.example.technical.presentation.TipoTecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.data.repository.TiposRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TiposViewModel(private val repository: TiposRepository, private val TipoId: Int) :
    ViewModel() {

    var uiState = MutableStateFlow(TiposUiState())
        private set

    val Tipos = repository.getTipos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onDescriptionChange(description: String){
        uiState.update {
            it.copy(Descripcion = description)
        }
    }
    init {
        viewModelScope.launch {
            val Type = repository.getTipoById(TipoId)

            Type?.let {
                uiState.update {
                    it.copy(
                        TipoId = Type.TipoId ?: 0,
                        Descripcion = Type.Descripción?: ""
                    )
                }
            }
        }
    }

    fun saveTipo(Type: TiposEntity){
        viewModelScope.launch {
            repository.saveTipo(uiState.value.toEntity())
            uiState.value = TiposUiState()
        }
    }

    fun DeleteTipo(){
        viewModelScope.launch {

        }
    }


    data class TiposUiState(
        val TipoId: Int = 0,
        val Descripcion: String = "",
        val errorDescription: String = "",
    )

    fun TiposUiState.toEntity() : TiposEntity{
        return TiposEntity(
            TipoId = TipoId,
            Descripción = Descripcion
        )
    }
}