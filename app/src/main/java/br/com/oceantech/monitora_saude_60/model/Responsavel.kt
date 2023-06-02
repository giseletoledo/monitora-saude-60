package br.com.oceantech.monitora_saude_60.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responsaveis")
data class Responsavel(
    @PrimaryKey
    val celular: String,
    val nome: String
)

