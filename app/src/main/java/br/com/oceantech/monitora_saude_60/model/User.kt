package br.com.oceantech.monitora_saude_60.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var birthday: LocalDate,
    var login: String,
    var password: String,
    var phone: String
)
