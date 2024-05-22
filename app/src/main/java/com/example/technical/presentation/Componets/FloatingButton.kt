package com.example.technical.presentation.Componets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloatingButton(onAddTecnico: () -> Unit) {
    FloatingActionButton(
        onClick = onAddTecnico
    ) {
        Icon(Icons.Filled.Add, "Agregar")
    }
}


@Preview
@Composable
fun FloatingButtonPreview() {
    FloatingButton {}
}