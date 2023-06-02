package br.com.oceantech.monitora_saude_60.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.oceantech.monitora_saude_60.database.converters.DateConverter
import br.com.oceantech.monitora_saude_60.database.converters.LocalTimeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "medicamentos")
@TypeConverters(LocalTimeConverter::class, DateConverter::class)
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val nome: String,
    val dosagem: Double,
    val intervaloDoses: Int,
    val dataInicio: LocalDate,
    val horarios: List<LocalTime>,
    val tomou: Boolean = false, // novo campo para indicar se o medicamento foi tomado
    val duracao: Int
)
