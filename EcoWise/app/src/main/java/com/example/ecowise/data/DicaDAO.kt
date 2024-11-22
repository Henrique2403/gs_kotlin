package com.example.ecowise.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ecowise.model.Dica
import kotlinx.coroutines.flow.Flow


@Dao
interface DicaDao {
    // Método para inserir uma dica
    @Insert
    fun insertDica(dica: Dica?): Long

    // Método para deletar uma dica
    @Delete
    fun deleteDica(dica: Dica?): Int

    @Query("SELECT * FROM dicas")
    fun allDicas(): Flow<List<Dica>>
}

