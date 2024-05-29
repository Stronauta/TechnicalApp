package com.example.technical.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen{
    @Serializable
    object TechnicalListScreen : Screen()

    @Serializable
    object TiposTecnicoList: Screen()

    @Serializable
    object ServicioListScreen: Screen()

    @Serializable
    data class Tecnico(val id: Int) : Screen()

    @Serializable
    data class TiposTecnico(val id: Int) : Screen()

    @Serializable
    data class Servicio(val id: Int) : Screen()


}