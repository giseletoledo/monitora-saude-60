package br.com.oceantech.monitora_saude_60.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val dosagem: Double,
    val intervaloDoses: Int,
    val dataInicio: LocalDate,
    val dataFim: LocalDate,
    val horarios: List<LocalTime>,
    val tomou: Boolean = false // novo campo para indicar se o medicamento foi tomado
)
