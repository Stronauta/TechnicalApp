package com.example.technical.presentation.TipoTecnico

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.presentation.Componets.TopAppBar

@Composable
fun TipoScreen(
    viewModel: TiposViewModel,
    navController: NavController
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


}


@Composable
fun TipoBodyScreen(
    uiState: TiposViewModel.TiposUiState,
    onDescripcionChange: (String) -> Unit,
    onSaveTipo: (TiposEntity) -> Unit
    //navController: NavController
){

    val context = LocalContext.current


    var descripcion by rememberSaveable { mutableStateOf("") }

    fun validaInput(): Boolean{
        return descripcion.isNotEmpty()
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = "Crear Tipo de Técnico") },) { innerPadding ->

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {

                OutlinedTextField(
                    maxLines = 1,
                    value = descripcion, // Use the separate state variable for the value
                    onValueChange = { descripcion = it }, // Update the state variable when the user changes the text field
                    label = { Text(text = "Descripción") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Descripción",
                            tint = if(!validaInput()) Color.Red else Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validaInput()
                )
                if(!validaInput()){
                    Text(
                        text = "Llene los campos requeridos",
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
                            onDescripcionChange(" ")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "new button"
                        )
                        Text(text = "Nuevo")
                    }

                    OutlinedButton(
                        onClick = {
                            if(!validaInput()){
                                onSaveTipo(
                                    TiposEntity(
                                        TipoId = uiState.TipoId,
                                        Descripción = uiState.Descripcion
                                    )
                                )
                                Toast.makeText(context, "No es posible guardar", Toast.LENGTH_SHORT).show()
                            }

                        }
                    ) {
                      Icon(
                          imageVector = Icons.Default.Add,
                          contentDescription = "save button"
                      )
                        Text(text = "Guardar")
                    }
                }

            }


        }
    }
}


@Preview
@Composable
fun TipoDetalleScreenPreview() {
    TipoBodyScreen(
        uiState = TiposViewModel.TiposUiState(),
        onDescripcionChange = {},
        onSaveTipo = {}
    )
}