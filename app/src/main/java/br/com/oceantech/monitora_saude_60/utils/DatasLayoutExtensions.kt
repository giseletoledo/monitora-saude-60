package br.com.oceantech.monitora_saude_60.utils

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private val locale = Locale("pt", "BR")

fun String.toDate(format: String): Date {
    val dateFormat = SimpleDateFormat(format, locale)
    return dateFormat.parse(this)!!
}

// Extensão para formatar data
fun Date.formatDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC") // define o fuso horário para UTC
    return dateFormat.format(this)
}

// Extensão para formatar data
fun LocalDate.formatDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(this)
}

// Extensão para formatar hora
fun Date.formatTime(): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(this)
}

// Extensão para setar o texto do TextInputLayout
var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }