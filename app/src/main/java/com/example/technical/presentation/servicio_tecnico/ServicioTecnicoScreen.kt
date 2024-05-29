package com.example.technical.presentation.servicio_tecnico

import MenuBox
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.technical.R
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.presentation.componets.TopAppBar
import com.example.technical.ui.theme.TechnicalTheme
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun ServiceScreen(
    viewModel: ServicioViewModel,
    navController: NavController
){

}

@Composable
fun ServiceBody(
    uiState: ServicioViewModel.ServicioUiState,
    onClienteChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onTecnicoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onTotalChange: (String) -> Unit,
    TecnicoList: List<TechnicalEntity>,
    onNewService: () -> Unit,
    onDeleteService: () -> Unit,

    onSaveClick: () -> Unit
){

    val scope = rememberCoroutineScope()


    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(
            title = "Registrar Servicio",
            onDrawerClicked = {
                scope.launch {}
            }
        ) },
    ){paddingValues ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ){

                OutlinedTextField(
                    label = { Text("Fecha") },
                    value = uiState.fecha,
                    onValueChange = onFechaChange,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Fecha",
                        )
                    },
                )

                OutlinedTextField(
                    label = { Text("Cliente") },
                    value = uiState.fecha,
                    onValueChange = onFechaChange,
                    placeholder = { Text("Ingrese el nombre del cliente") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Cliente",
                        )
                    },
                )

                var selectedOption by remember { mutableStateOf<TechnicalEntity?>(null) }

                MenuBox(
                    label = "Tecnico",
                    items = TecnicoList,
                    selected = selectedOption,
                    selectedItemString = {it?.let {
                        "Tecnico: ${it.tecnicoName}"
                    } ?: "Seleccione un tecnico"},
                    onItemSelected = {
                        onTecnicoChange(it?.tecnicoName ?: "")
                        selectedOption = it
                    },
                    itemTemplate = { Text(text = it.tecnicoName)},
                    isErrored = uiState.tecnicoId.isNullOrEmpty(), //ta mal xd, tengo que arregarlo
                )


                OutlinedTextField(
                    label = { Text("Descripcion") },
                    value = uiState.fecha,
                    onValueChange = onFechaChange,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Descripcion",
                        )
                    },
                )

                OutlinedTextField(
                    label = { Text("Total") },
                    value = uiState.fecha,
                    onValueChange = onFechaChange,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_moneda_90),
                            contentDescription = "money",
                        )
                    },
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    OutlinedButton(
                        onClick = {
                            onNewService()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "new button"
                        )
                        Text(text = "Nuevo")
                    }

                        OutlinedButton(
                            onClick = {
                                onDeleteService()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "delete button"
                            )
                            Text(text = "Eliminar")
                        }

                    OutlinedButton(
                        onClick = onSaveClick
                    )
                    {
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
fun ServiceBodyPreview(){
    TechnicalTheme {
        ServiceBody(
            uiState = ServicioViewModel.ServicioUiState(),
            onClienteChange = {},
            onFechaChange = {},
            onTecnicoChange = {},
            onDescripcionChange = {},
            onTotalChange = {},
            TecnicoList = listOf(
                TechnicalEntity(1, "Samir"),
                TechnicalEntity(2, "Abraha,"),
                TechnicalEntity(3, "Pepe")
            ),
            onSaveClick = {},
            onNewService = {},
            onDeleteService = {}
        )
    }
}