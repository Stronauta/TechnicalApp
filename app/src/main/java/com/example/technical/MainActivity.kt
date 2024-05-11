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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import com.example.technical.ui.theme.TechnicalTheme
import com.example.technical.data.local.database.TecnicoDatabase
import com.example.technical.data.local.entities.TechnicalEntity
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
            "tecnico.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        enableEdgeToEdge()
        setContent {
            TechnicalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Tecnicos",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val tecnicos: List<TechnicalEntity> by getTecnico().collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun saveTecnico(tecnico: TechnicalEntity) {
        GlobalScope.launch {
            tecnicoDb.technicalDao().save(tecnico)
        }
    }

    private fun getTecnico(): Flow<List<TechnicalEntity>> {
        return tecnicoDb.technicalDao().findAll()
    }

    private fun deleteTecnico(tecnico: TechnicalEntity) {
        GlobalScope.launch {
            tecnicoDb.technicalDao().delete(tecnico)
        }
    }
}


