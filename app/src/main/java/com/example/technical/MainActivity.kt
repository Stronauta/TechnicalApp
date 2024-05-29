package com.example.technical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.technical.ui.theme.TechnicalTheme
import com.example.technical.data.local.database.TecnicoDatabase
import com.example.technical.data.repository.TechnicalRepository
import com.example.technical.data.repository.TiposRepository
import com.example.technical.presentation.componets.TopAppBar
import com.example.technical.presentation.tecnico.TechnicalListScreen
import com.example.technical.presentation.tecnico.TecnicoViewModel
import com.example.technical.presentation.tecnico.TechnicalScreen
import com.example.technical.presentation.tipo_tecnico.TipoListScreen
import com.example.technical.presentation.tipo_tecnico.TipoScreen
import com.example.technical.presentation.tipo_tecnico.TiposViewModel
import com.example.technical.presentation.navigation.Screen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var tecnicoDb: TecnicoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tecnicoDb = Room.databaseBuilder(
            this,
            TecnicoDatabase::class.java,
            "Tecnicos.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val repository = TechnicalRepository(tecnicoDb.technicalDao())
        val tipoRepository = TiposRepository(tecnicoDb.tiposDao())

        enableEdgeToEdge()
        setContent {
            TechnicalTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(Modifier.requiredWidth(220.dp)) {
                            Text("Registro de Tecnicos", modifier = Modifier.padding(16.dp))
                            Divider()
                            NavigationDrawerItem(
                                label = { Text(text = "Lista de tecnicos") },
                                selected = false,
                                onClick = { navController.navigate(Screen.TechnicalListScreen) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Lista de tecnicos"
                                    )
                                }
                            )

                            NavigationDrawerItem(
                                label = { Text(text = "Lista de tipos tecnicos") },
                                selected = false,
                                onClick = { navController.navigate(Screen.TiposTecnicoList) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Lista de tipos tecnicos"
                                    )
                                }
                            )
                        }
                    },
                    drawerState = drawerState,
                    content = {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = "Your Title",
                                    onDrawerClicked = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                )
                            },
                            content = { padding ->
                                NavHost(
                                    navController = navController,
                                    startDestination = Screen.TechnicalListScreen,
                                    modifier = Modifier.padding(padding)
                                ) {

                                    composable<Screen.TechnicalListScreen> {
                                        TechnicalListScreen(
                                            viewModel = viewModel {
                                                TecnicoViewModel(
                                                    repository,
                                                    0,
                                                    tipoRepository
                                                )
                                            },
                                            onTechnicalClickVer = {
                                                navController.navigate(Screen.Tecnico(it.tecnicoId ?: 0))
                                            },
                                            onAddTechnical = {
                                                navController.navigate(Screen.Tecnico(0))
                                            },
                                            navController = navController

                                        )
                                    }

                                    composable<Screen.TiposTecnicoList> {
                                        TipoListScreen(
                                            viewModel = viewModel { TiposViewModel(tipoRepository, 0) },
                                            navController = navController,
                                            onTipoClick = {
                                                navController.navigate(Screen.TiposTecnico(it.TipoId ?: 0))
                                            },
                                            onAddTipo = {
                                                navController.navigate(Screen.TiposTecnico(0))
                                            }
                                        )
                                    }

                                    composable<Screen.Tecnico> {
                                        val args = it.toRoute<Screen.Tecnico>()
                                        TechnicalScreen(
                                            viewModel = viewModel {
                                                TecnicoViewModel(
                                                    repository,
                                                    args.id,
                                                    tipoRepository
                                                )
                                            },
                                            navController = navController
                                        )
                                    }

                                    composable<Screen.TiposTecnico> {
                                        val args = it.toRoute<Screen.TiposTecnico>()
                                        TipoScreen(
                                            viewModel = viewModel {
                                                TiposViewModel(
                                                    tipoRepository,
                                                    args.id
                                                )
                                            },
                                            navController = navController
                                        )

                                    }
                                }
                            }
                        )
                    }
                )

            }
        }
    }
}
