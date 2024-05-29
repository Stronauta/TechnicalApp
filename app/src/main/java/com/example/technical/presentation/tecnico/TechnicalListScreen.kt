package com.example.technical.presentation.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.ui.theme.TechnicalTheme
import com.example.technical.presentation.componets.TopAppBar
import com.example.technical.presentation.componets.FloatingButton
import com.example.technical.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun TechnicalListScreen(
    viewModel: TecnicoViewModel,
    onTechnicalClickVer: (TechnicalEntity) -> Unit,
    onAddTechnical: () -> Unit,
    navController: NavHostController,
){
    val technical by viewModel.technicals.collectAsStateWithLifecycle()
    val tipoTecnico by viewModel.tipoTecnico.collectAsStateWithLifecycle()

    TechnicalListBody(
        technical = technical,
        tipoTecnico = tipoTecnico,
        onAddTechnical = onAddTechnical,
        onTechnicalClickVer = onTechnicalClickVer,
        navController = navController,
    )
}

@Composable
fun TechnicalListBody(
    technical: List<TechnicalEntity>,
    tipoTecnico: List<TiposEntity>,
    onTechnicalClickVer: (TechnicalEntity) -> Unit,
    onAddTechnical: () -> Unit,
    navController: NavHostController
) {


    val scope = rememberCoroutineScope()
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Tecnicos",
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingButton(onAddTechnical)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {

            ElevatedCard {
                Row {
                    Text(
                        text = "#",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.100f)
                    )

                    Text(
                        text = "Nombre",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.25f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sueldo",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.40f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tipo Técnico",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.25f)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(technical) { Technical ->

                    val tipoEntity = tipoTecnico.find { it.TipoId == Technical.tipo }
                    val tipoDescription = tipoEntity?.Descripción ?: "Unknown"

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                color = if ((Technical.tecnicoId ?: 0) % 2 == 0)
                                    Color(0xFFFFFFFF)
                                else
                                    Color(0xFFFFFFFF)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTechnicalClickVer(Technical) }
                                .padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = Technical.tecnicoId.toString() + ". ",
                                modifier = Modifier.weight(0.100f)
                            )

                            Text(
                                text = Technical.tecnicoName,
                                modifier = Modifier.weight(0.25f)
                            )

                            Text(
                                text = "RD " + Technical.monto.toString(),
                                modifier = Modifier.weight(0.300f)
                            )

                            Text(
                                text = tipoDescription,
                                modifier = Modifier.weight(0.300f)
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun TechnicalListPreview() {
    val technical = listOf(
        TechnicalEntity(
            tecnicoId = 1,
            tecnicoName = "a",
            monto = 1000.0
        )
    )
    TechnicalTheme {
        val technical = listOf(
            TechnicalEntity(
                tecnicoId = 1,
                tecnicoName = "a",
                monto = 1000.0,
                tipo = 1
            )
        )
        TechnicalListBody(
            technical = technical,
            onTechnicalClickVer = {},
            onAddTechnical = {},
            navController = rememberNavController(),
            tipoTecnico = emptyList()
        )
    }
}


