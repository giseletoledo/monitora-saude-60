package br.com.oceantech.monitora_saude_60.database.converters

import androidx.room.TypeConverter
import java.time.LocalTime

class LocalTimeConverter {
    @TypeConverter
    fun fromString(value: String?): List<LocalTime>? {
        return value?.split(",")?.map { LocalTime.parse(it.trim()) }
    }

    @TypeConverter
    fun fromList(list: List<LocalTime>?): String? {
        return list?.joinToString(",") { it.toString() }
    }
}
