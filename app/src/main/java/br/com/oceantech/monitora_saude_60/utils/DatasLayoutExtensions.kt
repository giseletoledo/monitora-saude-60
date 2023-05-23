package br.com.oceantech.monitora_saude_60.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val locale = Locale("pt", "BR")


// Extensão para formatar data
fun LocalDate.formatDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", locale)
    val zoneId = ZoneId.of("UTC")
    val zonedDateTime = this.atStartOfDay(zoneId)
    return dateFormat.format(zonedDateTime)
}
