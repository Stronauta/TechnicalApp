package com.example.technical.presentation.tipo_tecnico

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.technical.Screen
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.presentation.componets.TopAppBar
import com.example.technical.presentation.componets.Notificacion
import kotlinx.coroutines.launch

@Composable
fun TipoScreen(
    viewModel: TiposViewModel,
    navController: NavHostController
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.Tipos.collectAsStateWithLifecycle()
    TipoBodyScreen(
        uiState = uiState,
        onDescripcionChange = viewModel::onDescriptionChange,
        onSaveTipo = {
            viewModel.saveTipo()
        },
        navController = navController,
        onNewTipo = {
            viewModel.newTipo()
        },
        onDeleteTipo = {
            viewModel.DeleteTipo()
        }
    )
}


@Composable
fun TipoBodyScreen(
    uiState: TiposViewModel.TiposUiState,
    onDescripcionChange: (String) -> Unit,
    onSaveTipo: () -> Unit,
    navController: NavHostController,
    onNewTipo: () -> Unit,
    onDeleteTipo: () -> Unit
){

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }


    val scope = rememberCoroutineScope()
    var drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet( Modifier.requiredWidth(220.dp)) {
                Text("Registro Tipo de Técnicos", modifier = Modifier.padding(16.dp))
                Divider()

                NavigationDrawerItem(
                    label = {  Text(text = "Registro tipo de técnicos")  },
                    selected = false,
                    onClick = { navController.navigate(Screen.TiposTecnicoList) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Lista tipo de técnicos"
                        )
                    }

                )

                NavigationDrawerItem(
                    label = {  Text(text = "Registro de técnicos")  },
                    selected = false,
                    onClick = { navController.navigate(Screen.TechnicalListScreen) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Lista  de técnicos"
                        )
                    }

                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                title = "Crear Tipo de Técnico",
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) },
        )
        { innerPadding ->

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {

                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        maxLines = 1,
                        value = uiState.Descripcion,
                        onValueChange = onDescripcionChange ,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Descripción",
                                tint = if(!uiState.esDescripcionError.isNullOrEmpty()) Color.Red else Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = !uiState.esDescripcionError.isNullOrEmpty(),
                    )
                    if(!uiState.esDescripcionError.isNullOrEmpty()){
                        Text(text = uiState.esDescripcionError ?: "", color = Color.Red)
                        Notificacion(Mensaje = "Debe ingresar una descripción")
                    }


                    Spacer(modifier = Modifier.height(15.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "Casillas limpias", Toast.LENGTH_SHORT).show()

                                onNewTipo()
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
                                if (uiState.Descripcion.isNotEmpty()) {
                                    onSaveTipo()
                                    if(uiState.esDescripcionError.isNullOrEmpty()){
                                        if(uiState.TipoId == 0 || uiState.TipoId == null){
                                            Toast.makeText(context, "Tipo técnico ha sido creado", Toast.LENGTH_SHORT)
                                                .show()
                                        } else {
                                            Toast.makeText(context, "Tipo técnico ha sido actualizado", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                        navController.navigate(Screen.TiposTecnicoList)
                                    }
                                } else {
                                    Toast.makeText(context, "Descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            if(uiState.TipoId == null || uiState.TipoId == 0){
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "save button"
                                )
                                Text(text = "Guardar")
                            } else{
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "update button"
                                )
                                Text(text = "Actualizar")
                            }
                        }

                        if(uiState.TipoId != null && uiState.TipoId != 0) {

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
                                                "Eliminar Tipo de Técnico",
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            "¿Esta seguro de eliminar el tipo de técnico ?",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                showDialog = false
                                                onDeleteTipo()
                                                navController.navigate(Screen.TiposTecnicoList)
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
}


@Preview
@Composable
fun TipoDetalleScreenPreview() {
    TipoBodyScreen(
        uiState = TiposViewModel.TiposUiState(),
        onDescripcionChange = {},
        onSaveTipo = {},
        navController = rememberNavController(),
        onNewTipo = {},
        onDeleteTipo = {}
    )
}

