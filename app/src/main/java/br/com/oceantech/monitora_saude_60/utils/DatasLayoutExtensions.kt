package br.com.oceantech.monitora_saude_60.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val locale = Locale("pt", "BR")


// Extensão para formatar data
fun LocalDate.formatDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(this)
}
