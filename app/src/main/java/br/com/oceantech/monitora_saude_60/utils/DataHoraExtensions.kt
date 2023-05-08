package br.com.oceantech.monitora_saude_60.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


// Função de extensão para converter uma String em LocalDate, ou retornar null se não for possível
fun String?.toLocalDateOrNull(): LocalDate? {
    if (this == null) {
        return null
    }
    try {
        return LocalDate.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        return null
    }
}

// Função de extensão para converter uma String com horários separados por vírgula em uma lista de LocalTime, ou retornar null se não for possível
fun String?.toHorariosListOrNull(): List<LocalTime>? {
    if (this == null) {
        return null
    }
    try {
        return this.split(",").map { LocalTime.parse(it.trim(), DateTimeFormatter.ofPattern("HH:mm")) }
    } catch (e: Exception) {
        return null
    }
}

