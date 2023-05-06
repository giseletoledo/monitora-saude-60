package br.com.oceantech.monitora_saude_60.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromString(value: String?): List<LocalDate>? {
        return value?.split(",")?.map { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToString(dates: List<LocalDate>?): String? {
        return dates?.joinToString(",") { it.toString() }
    }
}


