package com.example.technical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.technical.ui.theme.TechnicalTheme
import com.example.technical.data.local.database.TecnicoDatabase
import com.example.technical.data.repository.TechnicalRepository
import com.example.technical.presentation.TechnicalListScreen
import com.example.technical.presentation.TecnicoViewModel
import com.example.technical.presentation.TechnicalScreen
import kotlinx.serialization.Serializable


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
        enableEdgeToEdge()
        setContent {
            TechnicalTheme {
                val navController = rememberNavController()


                NavHost(navController = navController, startDestination = Screen.TechnicalListScreen)
                {

                    composable<Screen.TechnicalListScreen>{
                        TechnicalListScreen(
                            viewModel = viewModel { TecnicoViewModel(repository, 0) },
                            onTechnicalClickVer = {
                                navController.navigate(Screen.Tecnico(it.tecnicoId ?: 0))
                            }

                        )


                    }

                    composable<Screen.Tecnico>{
                        val args = it.toRoute<Screen.Tecnico>()
                        TechnicalScreen(
                            viewModel = viewModel { TecnicoViewModel(repository, args.id) },
                            navController = navController  // Pass the NavController here
                        )
                    }
                }
            }
        }
    }
}

sealed class Screen{
    @Serializable
    object TechnicalListScreen : Screen()

    @Serializable
    data class Tecnico(val id: Int) : Screen()
}

@Preview
@Composable
fun preview(){
    TechnicalTheme {

    }

}