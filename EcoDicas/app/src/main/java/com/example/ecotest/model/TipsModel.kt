package com.example.ecotest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dicas")
data class TipsModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descricao: String
)