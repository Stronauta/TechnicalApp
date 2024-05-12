package com.example.technical.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle


import androidx.compose.ui.res.painterResource
import com.example.technical.R

import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.ui.theme.TechnicalTheme


@Composable
fun TechnicalScreen(
    viewModel: TecnicoViewModel
){
    val tecnicos by viewModel.technicals.collectAsStateWithLifecycle()
    TechnicalBody(
        onSaveTechnical = {
            viewModel.saveTechnical(it)
        }
    )
}


@Composable
fun TechnicalBody(onSaveTechnical: (TechnicalEntity) -> Unit) {

    var technicalId by remember { mutableStateOf("") }
    var technicalName by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf(0.0) }

    var showError by remember { mutableStateOf(false) } // Estado para indicar si se debe mostrar errores
    val isNameError = showError && (technicalName.isBlank() || technicalName.any { it.isDigit() })
    val isMontoError = showError && (monto <= 0.0)

    val context = LocalContext.current

    fun validateInput(): Boolean{
        return technicalName.isNotEmpty() && monto > 0.0
    }

    fun validarNombreDuplicado(): Boolean{
        return technicalName.any { it.equals(technicalName) }
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            OutlinedTextField(
                maxLines = 1,
                label = { Text("Nombre") },
                value = technicalName,
                onValueChange = { name ->
                    val filtrarName = name.take(30).filter { it.isLetter() || it.isWhitespace() }
                    technicalName = filtrarName
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "person",
                        tint = if (isNameError) Color.Red else Color.Gray
                    )
                },
                isError = isNameError
            )
            if (isNameError) {
                Text(
                    text = "El nombre es obligatorio",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                maxLines = 1,
                label = { Text("Sueldo") },
                value = monto.toString(),
                onValueChange = { newValue ->
                    val newText = newValue.takeIf { it.matches(Regex("""^\d{0,5}(\.\d{0,2})?$""")) } ?: monto.toString()
                    monto = newText.toDoubleOrNull() ?: 0.0
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_moneda_90),
                        contentDescription = "moneda",
                        tint = if (isMontoError) Color.Red else Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                isError = isMontoError
            )
            if (isMontoError) {
                Text(
                    text = "El sueldo debe ser mayor a 0",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        showError = false
                        if (technicalName.isNotEmpty() || monto > 0.0) {
                            technicalName = ""
                            monto = 0.0

                            Toast.makeText(context, "Datos limpiados", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "new button"
                    )
                    Text(text = "Nuevo")

                }

                OutlinedButton(
                    onClick = {
                        if (validateInput()) {
                            if (validarNombreDuplicado()) {
                                onSaveTechnical(
                                    TechnicalEntity(
                                        tecnicoId = technicalId.toIntOrNull(),
                                        tecnicoName = technicalName,
                                        monto = monto
                                    )
                                )
                                technicalId = ""
                                technicalName = ""
                                monto = 0.0

                                Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
                                showError = false
                            } else {
                                Toast.makeText(context, "¡Ya existe un técnico con ese nombre!", Toast.LENGTH_SHORT).show()
                                showError = true
                            }
                        } else {
                            Toast.makeText(context, "Ingrese los datos correctamente", Toast.LENGTH_SHORT).show()
                            showError = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "save button"
                    )
                    Text(text = "Guardar")
                }
            }
        }
    }
}


@Preview
@Composable
private fun TecnicoBodyPreview(){
    TechnicalTheme{
        TechnicalBody() {

        }
    }
}