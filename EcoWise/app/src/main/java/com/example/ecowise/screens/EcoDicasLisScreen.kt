import androidx.compose.foundation.layout.* // Certifique-se de que este import está incluído
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecowise.model.Dica

@Composable
fun EcoDicasListScreen(viewModel: DicaViewModel, onNavigateToAdd: () -> Unit) {
    val dicas by viewModel.dicas.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val filteredDicas = dicas.filter { dica: Dica ->
        dica.titulo?.contains(searchQuery, ignoreCase = true) == true ||
                dica.descricao?.contains(searchQuery, ignoreCase = true) == true
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("EcoDicas", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Pesquisar") },
            modifier = Modifier.fillMaxWidth()
        )

        // LazyColumn para exibir a lista filtrada de dicas
        LazyColumn(modifier = Modifier.weight(1f)) { // Modificador para ocupar espaço restante
            items(filteredDicas) { dica ->
                EcoDicaItem(dica)
            }
        }

        // FloatingActionButton para adicionar novas dicas
        FloatingActionButton(onClick = { onNavigateToAdd() }) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar Dica")
        }
    }
}

@Composable
fun EcoDicaItem(dica: Dica) {
    Column(modifier = Modifier.padding(8.dp)) {
        dica.titulo?.let { Text(it, style = MaterialTheme.typography.titleMedium) }
        dica.descricao?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
    }
}
