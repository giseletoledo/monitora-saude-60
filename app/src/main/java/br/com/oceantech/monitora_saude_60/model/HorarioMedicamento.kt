package br.com.oceantech.monitora_saude_60.model

import java.time.LocalTime

data class HorarioMedicamento(
    val nome: String,
    val hora: LocalTime
)
