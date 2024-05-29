package com.example.technical.presentation.servicio_tecnico

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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.technical.data.local.entities.ServiciosTecnicoEntity
import com.example.technical.presentation.componets.FloatingButton
import com.example.technical.presentation.componets.TopAppBar
import com.example.technical.ui.theme.TechnicalTheme
import kotlinx.coroutines.launch

@Composable
fun ServiceListScreen(
    viewModel: ServicioViewModel,
    onServicioClick: (ServiciosTecnicoEntity) -> Unit,
    onAddService: () -> Unit,
    navController: NavController
){
    val serviceList by viewModel.Servicio.collectAsStateWithLifecycle()

    ServiceListBody(
        serviceList = serviceList,
        onServicioClick = onServicioClick,
        onAddService = onAddService
    )

}


@Composable
fun ServiceListBody(
    serviceList: List<ServiciosTecnicoEntity>,
    onServicioClick: (ServiciosTecnicoEntity) -> Unit,
    onAddService: () -> Unit
){

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Servicios",
                onDrawerClicked = {
                    scope.launch {
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingButton(onAddService)
        }
    ){ paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ElevatedCard {
                Row{
                    Text(
                        text = "#",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .weight(0.150f)
                            .padding(horizontal = 16.dp)
                    )

                    Text(
                        text = "Fecha",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f).padding(16.dp)
                    )

                    Text(
                        text = "Cliente",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f).padding(16.dp)
                    )

                    Text(
                        text = "Tecnico",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f).padding(16.dp)
                    )

                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f).padding(16.dp)
                    )

                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(serviceList) { Services ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                Color(0xFFFFFFFF)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onServicioClick(Services) }
                                .padding(horizontal = 4.dp)
                        ){

                            Text(
                                text = Services.ServicioId.toString() + ". ",
                                modifier = Modifier
                                    .weight(0.150f)
                                    .padding(horizontal = 15.dp)
                            )

                            Text(
                                text = Services.Fecha,
                                modifier = Modifier.weight(0.25f)
                                    .padding(horizontal = 15.dp)
                            )

                            Text(
                                text = Services.Cliente,
                                modifier = Modifier.weight(0.25f)
                                    .padding(horizontal = 15.dp)
                            )

                            Text(
                                text = Services.TecnicoId.toString(),
                                modifier = Modifier.weight(0.150f)
                                    .padding(horizontal = 15.dp)
                            )

                            Text(
                                text = "$ " + Services.Total.toString(),
                                modifier = Modifier.weight(0.300f)
                                    .padding(horizontal = 15.dp)
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
fun ServiceListBodyPreview(){

    val serviceList = listOf(
        ServiciosTecnicoEntity(
            ServicioId = 1,
            Fecha = "05/28/2022",
            Cliente = "Samir",
            TecnicoId = 1,
            Total = 1000.0
        ),
        ServiciosTecnicoEntity(
            ServicioId = 2,
            Fecha = "05/29/2022",
            Cliente = "Samir2",
            TecnicoId = 2,
            Total = 2000.0
        )
    )

    TechnicalTheme{
        ServiceListBody(
            serviceList = serviceList,
            onServicioClick = {},
            onAddService = {}
        )
    }

}