package com.example.technical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.technical.ui.theme.TechnicalTheme
import com.example.technical.data.local.database.TecnicoDatabase
import com.example.technical.data.local.entities.TechnicalEntity
import com.example.technical.data.repository.TechnicalRepository
import com.example.technical.presentation.TechnicalListBody
import com.example.technical.presentation.TechnicalListScreen
import com.example.technical.presentation.TechnicalScreen
import com.example.technical.presentation.TecnicoViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
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
        enableEdgeToEdge()
        setContent {
            TechnicalTheme {
                Surface {
                    val viewModel: TecnicoViewModel = viewModel(
                        factory = TecnicoViewModel.provideFactory(repository)
                    )
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(8.dp)
                        ){
                            Text(
                                text = "Tecnicos",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )


                            TechnicalScreen(viewModel = viewModel)
                            TechnicalListScreen(viewModel = viewModel,
                                onTechnicalClickVer = {}
                            )

                        }


                    }
                }
            }
        }
    }
}



