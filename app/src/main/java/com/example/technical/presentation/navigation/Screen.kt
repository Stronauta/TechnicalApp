package com.example.technical.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen{
    @Serializable
    object TechnicalListScreen : Screen()

    @Serializable
    object TiposTecnicoList: Screen()

    @Serializable
    data class Tecnico(val id: Int) : Screen()

    @Serializable
    data class TiposTecnico(val id: Int) : Screen()

}