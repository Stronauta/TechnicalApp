package com.example.technical.presentation.tipo_tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.data.repository.TiposRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class TiposViewModel(
    private val repository: TiposRepository,
    private val TipoId: Int
) :
    ViewModel() {

    val regexDescripcion = Regex("[\\p{L} ]*")


    var uiState = MutableStateFlow(TiposUiState())
        private set

    val Tipos = repository.getTipos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onDescriptionChange(descripcion: String) {
        if (descripcion.matches(regexDescripcion) && !descripcion.startsWith(" ")) {
            uiState.update {
                it.copy(Descripcion = descripcion)
            }
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

    fun saveTipo() {
        viewModelScope.launch {
            var comprobado = true

            uiState.update {
                it.copy(esDescripcionError = null)
            }

            if(uiState.value.TipoId == 0){
                uiState.update {
                    it.copy(TipoId = null)
                }
            }

            if (uiState.value.Descripcion.isEmpty()) {
                uiState.update {
                    it.copy(esDescripcionError = "Campo requerido")
                }
                comprobado = false
            }

            if (uiState.value.Descripcion.isNotEmpty() && !regexDescripcion.matches(uiState.value.Descripcion)) {
                uiState.update {
                    it.copy(esDescripcionError = "Formato no valido")
                }
                comprobado = false
            }

            if(!descripcionTipoExiste()){
                uiState.update {
                    it.copy(esDescripcionError = "Descripcion ya existe")
                }
                comprobado = false
            }

            if(!comprobado){
                return@launch
            }

            uiState.update {
                it.copy(guardado = true)
            }

            // Save the Tipo if the checks pass
            repository.saveTipo(uiState.value.toEntity())
        }
    }

    fun newTipo(){
        viewModelScope.launch {
            uiState.value = TiposUiState()
        }
    }


    fun DeleteTipo(){
        viewModelScope.launch {
            repository.deletecTipo(uiState.value.toEntity())
        }
    }

    suspend fun descripcionTipoExiste(): Boolean {
        val descripcion = repository.getDescripcionTipo(uiState.value.Descripcion, uiState.value.TipoId ?: 0)
        return descripcion == null
    }


    data class TiposUiState(
        val TipoId: Int? = null,
        val Descripcion: String = "",
        var esDescripcionError: String? = null,
        var guardado: Boolean = false
    )

    fun TiposUiState.toEntity() : TiposEntity{
        return TiposEntity(
            TipoId = TipoId,
            Descripción = Descripcion
        )
    }
}