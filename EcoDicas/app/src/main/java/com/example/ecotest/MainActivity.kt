package com.example.ecotest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Query
import androidx.room.Room
import com.example.ecotest.data.AppDatabase
import com.example.ecotest.data.TipsDAO
import com.example.ecotest.model.TipsModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private lateinit var tipsDao: TipsDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "eco_dicas_db").build()
        tipsDao = db.tipDAO()

        setContent {
            EcoDicasApp(tipDao = tipsDao)
        }
    }
}

@Composable
fun EcoDicasApp(tipDao: TipsDAO) {
    var tips by remember { mutableStateOf<List<TipsModel>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<TipsModel>>(emptyList()) }
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        tips = tipDao.getAllDicas()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "Eco Dicas",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF81C784),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nicolas Estrella Porciuncula",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Henrique Copatti Cruz",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "RM 94236",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "RM 94751",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar Dicas") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                coroutineScope.launch {
                    searchResults = tipDao.searchDicas("%$searchQuery%")
                }
            }) {
                Text("Buscar")
            }
        }

        if (searchResults.isNotEmpty()) {
            Text(
                text = "Resultados da pesquisa:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            LazyColumn {
                items(searchResults) { tip ->
                    DicaItem(tip) { selectedTip ->
                        Toast.makeText(
                            context,
                            "Detalhes: ${selectedTip.descricao}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título da Dica") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição da Dica") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (titulo.isNotEmpty() && descricao.isNotEmpty()) {
                coroutineScope.launch {
                    val novaDica = TipsModel(titulo = titulo.trim(), descricao = descricao.trim())
                    android.util.Log.d("TipsDAO", "Inserindo dica: Título = ${novaDica.titulo}, Descrição = ${novaDica.descricao}")

                    tipDao.insertDica(novaDica)
                    tips = tipDao.getAllDicas()
                }

                titulo = ""
                descricao = ""
            } else {
                android.util.Log.d("TipsDAO", "Os campos de título ou descrição estão vazios.")
            }
        }) {
            Text("Adicionar Dica")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de dicas
        Text(
            text = "Todas as Dicas:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tips) { tip ->
                DicaItem(tip) { clickedTip ->
                    Toast.makeText(context, "Título: ${clickedTip.titulo}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@Composable
fun DicaItem(tips: TipsModel, onClick: (TipsModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(tips) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF81C784))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Título teste",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Descrção teste"
            )
        }
    }
}
