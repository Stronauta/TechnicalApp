package com.example.technical.presentation.tecnico

import MenuBox
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.technical.R
import com.example.technical.presentation.navigation.Screen
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.presentation.componets.Notificacion
import com.example.technical.presentation.componets.TopAppBar
import com.example.technical.ui.theme.TechnicalTheme
import kotlinx.coroutines.launch

@Composable
fun TechnicalScreen(
    viewModel: TecnicoViewModel,
    navController: NavController
){
    val tipoTecnico by viewModel.tipoTecnico.collectAsStateWithLifecycle()
    viewModel.technicals.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TechnicalBody(
        uiState = uiState,
        onNewTechnical = {
            viewModel.newTechnical()
        },
        onSaveTechnical = {
            viewModel.saveTechnical()
        },
        onDeletedTechnical = {
            viewModel.deleteTechnical()
        },
        onNameChange = viewModel::onNameChange,
        onMontoChange = { monto -> viewModel.onSalaryChange(monto.toDoubleOrNull() ?: 0.0) },
        onTipoChange = viewModel::onTipoChange,
        tipoTecnico = tipoTecnico,
        navController = navController
    )
}


@Composable
fun TechnicalBody(
    uiState: TecnicoViewModel.TecnicoUiState,
    onNameChange: (String) -> Unit,
    onMontoChange: (String) -> Unit,
    onTipoChange: (String) -> Unit,
    onNewTechnical: () -> Unit,
    tipoTecnico: List<TiposEntity>,
    onSaveTechnical: () -> Unit,
    onDeletedTechnical: () -> Unit,
    navController: NavController
) {
    val viewModel: TecnicoViewModel = viewModel()

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var drawerState = rememberDrawerState(DrawerValue.Closed)

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(
            title = "Crear Técnico",
            onDrawerClicked = {
                scope.launch {}
            }
        ) },
    ) { innerPadding ->
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
                    label = { Text("Nombre") },
                    value = uiState.nombreTecnico,
                    onValueChange = onNameChange,
                    isError = !uiState.errorName.isNullOrEmpty(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "person",
                            tint = if (uiState.errorName.isNullOrEmpty()) Color.Gray else Color.Red
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                if(!uiState.errorName.isNullOrEmpty()){
                    Text(text = uiState.errorName ?: "", color = Color.Red, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 16.dp),)
                }


                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    maxLines = 1,
                    label = { Text("Sueldo") },
                    value = uiState.salarioTecnico.toString(),
                    onValueChange = { onMontoChange(it) },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_moneda_90),
                            contentDescription = "money",
                            tint = if (!uiState.errorSalary.isNullOrEmpty()) Color.Red else Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    isError = !uiState.errorSalary.isNullOrEmpty()
                )

                if(!uiState.errorSalary.isNullOrEmpty()){
                    Text(text = uiState.errorSalary ?: "", color = Color.Red, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 16.dp),)
                }

                Spacer(modifier = Modifier.height(4.dp))

                var selectedOption by remember { mutableStateOf<TiposEntity?>(null) }


                MenuBox(
                    label = "Tipo de Técnico",
                    items = tipoTecnico,
                    selected = selectedOption,
                    selectedItemString = { it?.let {
                        "${it.Descripción}"
                    } ?: "" },
                    onItemSelected = {
                        onTipoChange(it?.Descripción?: "")
                        selectedOption = it
                        uiState.tipoTecnico = it?.Descripción?: ""
                    },
                    itemTemplate = { Text(text = it.Descripción) },
                    isErrored = !uiState.errorTipo.isNullOrEmpty()
                )
                if(!uiState.errorTipo.isNullOrEmpty()){
                    Text(
                        text = "El tipo de técnico es obligatorio",
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
                            onNewTechnical()
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
                            scope.launch {
                                if (uiState.nombreTecnico.isNotEmpty() && uiState.salarioTecnico > 0 && uiState.tipoTecnico.isNotEmpty()) {

                                        onSaveTechnical()
                                        if(uiState.isSaving || (uiState.errorName.isNullOrEmpty() && uiState.errorSalary.isNullOrEmpty() && uiState.errorTipo.isNullOrEmpty())){

                                            if(uiState.tecnicoId != 0){
                                                Toast.makeText(context, "Datos Actualizados", Toast.LENGTH_SHORT).show()
                                            }
                                            else{
                                                Toast.makeText(context, "Datos Guardados", Toast.LENGTH_SHORT).show()
                                            }
                                            navController.navigate(Screen.TechnicalListScreen)


                                        }
                                } else {
                                    Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        if (uiState.tecnicoId == 0 || uiState.tecnicoId == null) {
                            Text(text = "Guardar")
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "save button"
                            )
                        } else {
                            Text(text = "Actualizar")
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                        }
                    }

                    if(uiState.tecnicoId != null && uiState.tecnicoId != 0){

                        OutlinedButton(
                            onClick = {
                                showDialog = true

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "delete button"
                            )
                            Text(text = "Eliminar")
                        }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Warning,
                                            contentDescription = "Warning",
                                            tint = Color(0xFFDAA504)
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            "Eliminar él Técnico",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                },
                                text = {
                                    Text(
                                        "¿Esta seguro de eliminar él técnico ?",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDialog = false
                                            onDeletedTechnical()
                                            navController.navigate(Screen.TechnicalListScreen)
                                        },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = Color.Red
                                        )
                                    ) {
                                        Text("Confirmar")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { showDialog = false }
                                    ) {
                                        Text("Cancelar")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}



@Preview
@Composable
private fun TecnicoBodyPreview(){
    TechnicalTheme{
        TechnicalBody(
            uiState = TecnicoViewModel.TecnicoUiState(),
            onNameChange = {},
            onMontoChange = {},
            onSaveTechnical = {},
            navController = rememberNavController(),
            onTipoChange = {},
            onNewTechnical = {},
            tipoTecnico = listOf(
                TiposEntity(1, "A"),
                TiposEntity(2, "B"),
                TiposEntity(3, "C")
            ),
            onDeletedTechnical = {}

        )
    }
}


