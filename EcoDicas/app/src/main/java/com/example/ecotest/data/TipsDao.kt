package com.example.ecotest.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecotest.model.TipsModel

@Dao
interface TipsDAO {
    @Insert
    suspend fun insertDica(dica: TipsModel){
    Log.d("TipsDAO", "Inserindo dica: Título = ${dica.titulo}, Descrição = ${dica.descricao}")}


    @Query("SELECT * FROM dicas")
    suspend fun getAllDicas(): List<TipsModel>

    @Query("SELECT * FROM dicas WHERE titulo LIKE :searchQuery")
    suspend fun searchDicas(searchQuery: String): List<TipsModel>
}