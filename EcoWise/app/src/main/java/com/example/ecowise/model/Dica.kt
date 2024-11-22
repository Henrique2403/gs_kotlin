package com.example.ecowise.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "dicas")
class Dica(titulo: String, descricao: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var titulo: String? = null
    var descricao: String? = null
}

