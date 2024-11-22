import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecowise.data.AppDatabase
import com.example.ecowise.model.Dica
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DicaViewModel(application: Application) : AndroidViewModel(application) {
    // Injeção de dependência sugerida para facilitar testes e modularidade
    private val dicaDao = AppDatabase.getInstance(application).dicaDao()

    // `Flow` reativo para atualizar a lista automaticamente
    val dicas: StateFlow<List<Dica>> = dicaDao.allDicas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Adiciona uma dica ao banco de dados
    fun addDica(dica: Dica) {
        viewModelScope.launch {
            try {
                dicaDao.insertDica(dica)
            } catch (e: Exception) {
                // Trate o erro adequadamente (exemplo: log ou mensagem ao usuário)
                e.printStackTrace()
            }
        }
    }
}
