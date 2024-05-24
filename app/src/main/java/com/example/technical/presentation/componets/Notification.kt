package com.example.technical.presentation.componets

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun Notificacion(Mensaje: String ){
    val context = LocalContext.current

    Toast.makeText(context, Mensaje, Toast.LENGTH_SHORT).show()
}