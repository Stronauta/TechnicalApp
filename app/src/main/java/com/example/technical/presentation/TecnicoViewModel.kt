package com.example.technical.presentation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.repository.TechnicalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TecnicoViewModel(private val repository: TechnicalRepository) : ViewModel() {

    val technicals = repository.getTecnico()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun saveTechnical(technical: TechnicalEntity){
        viewModelScope.launch {
            repository.saveTecnico(technical)
        }
    }

    companion object{
        fun provideFactory(
            repository: TechnicalRepository
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(){
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return TecnicoViewModel(repository) as T
                }
            }
    }

}