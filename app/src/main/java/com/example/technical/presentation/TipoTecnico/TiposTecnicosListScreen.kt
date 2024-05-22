package com.example.technical.presentation.TipoTecnico

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

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.technical.R
import com.example.technical.data.local.entities.TiposEntity
import com.example.technical.ui.theme.TechnicalTheme
import com.example.technical.presentation.Componets.TopAppBar

@Composable
fun TipoListScreen(
    viewModel: TiposViewModel,
    onTipoClick: (TiposEntity) -> Unit
) {
    val TiposList by viewModel.Tipos.collectAsStateWithLifecycle()
    TiposListBody(
        tiposList = TiposList,
        onTipoClickVer = onTipoClick
    )
}


@Composable
fun TiposListBody(
    tiposList: List<TiposEntity>,
    onTipoClickVer: (TiposEntity) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = "Tipos de Tecnicos") }) { innerPadding ->

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (tiposList.isNotEmpty()) {
                ElevatedCard {
                    Row {
                        Text(
                            text = "#",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .weight(0.200f)
                                .padding(horizontal = 20.dp)
                        )

                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(0.300f)

                        )

                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(tiposList) { tipos ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onTipoClickVer(tipos) }
                                    .padding(horizontal = 4.dp)
                            ) {

                                Text(
                                    text = tipos.TipoId.toString() + ".  ",
                                    modifier = Modifier
                                        .weight(0.100f)
                                        .padding(horizontal = 15.dp)
                                )

                                Text(
                                    text = tipos.Descripción,
                                    modifier = Modifier.weight(0.200f)
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.icons8_ingeniero_80),
                                    contentDescription = "money",
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
fun TiposListBodyPreview() {
    val tipos = listOf(
        TiposEntity(
            TipoId = 1,
            Descripción = "Técnico de sistemas",
        ),
        TiposEntity(
            TipoId = 2,
            Descripción = "Técnico de redes",
        )
    )
    TechnicalTheme {
        TiposListBody(tiposList = tipos,
            onTipoClickVer = {}
        )
    }
}


