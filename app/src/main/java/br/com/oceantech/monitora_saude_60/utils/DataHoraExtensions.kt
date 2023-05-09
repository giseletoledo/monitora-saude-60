package br.com.oceantech.monitora_saude_60.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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