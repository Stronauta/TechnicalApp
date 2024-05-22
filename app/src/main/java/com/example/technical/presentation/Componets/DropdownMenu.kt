package com.example.technical.presentation.Componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MyExposedDropdownMenuBox(
    items: List<T>,
    selected: T?,
    selectedItemString: (T?) -> String,
    onItemSelected: (T?) -> Unit,
    label: String = "",
    itemTemplate: @Composable (T) -> Unit,
    isErrored: Boolean
){
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(selected ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded}
    )
    {
        OutlinedTextField(
            value = selectedItemString(selected),
            onValueChange = { },
            readOnly = true,
            label = { Text(text = label)},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            isError = isErrored,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            items.forEach { item ->
                DropdownMenuItem(
                    text = {itemTemplate(item)},
                    onClick = {
                        expanded = false
                        textFieldValue =  item.toString()
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun vista() {
    val items = listOf(
        Persona(nombre = "Pepe ", apellido = "pepito"),
        Persona(nombre = "Pepito", apellido = "pepe"),
        Persona(nombre = "a", apellido = "a" ),
    )

    var selectedItem by remember { mutableStateOf<Persona?>(null) }
    MyExposedDropdownMenuBox(
        items = items,
        label = "Prueba",
        selectedItemString = { it?.let { persona ->
            "${persona.nombre} ${persona.apellido}"
        } ?: ""},
        selected = selectedItem,
        onItemSelected = {
            selectedItem = it
        },
        itemTemplate = {
            Text(
                it.nombre,
                modifier = Modifier.padding(8.dp),
                color = Color.Black,

                style = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
            )
        },
        isErrored = false
    )
}
data class Persona(
    val nombre: String,
    val apellido: String
)