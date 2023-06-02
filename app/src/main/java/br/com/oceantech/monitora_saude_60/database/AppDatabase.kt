package br.com.oceantech.monitora_saude_60.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.oceantech.monitora_saude_60.dao.MedicamentoDao
import br.com.oceantech.monitora_saude_60.dao.ResponsavelDao
import br.com.oceantech.monitora_saude_60.dao.UserDao
import br.com.oceantech.monitora_saude_60.database.converters.DateConverter
import br.com.oceantech.monitora_saude_60.database.converters.TimeListConverter
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.model.Responsavel
import br.com.oceantech.monitora_saude_60.model.User

@Database(entities = [Medicamento::class, User::class, Responsavel::class], version = 4, exportSchema = false)
    @TypeConverters(DateConverter::class, TimeListConverter::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun medicamentoDao(): MedicamentoDao
        abstract fun userDao(): UserDao
        abstract fun responsavelDao(): ResponsavelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medicamento_db"
                ).addMigrations(Migration1To2(), Migration2To3(), Migration3To4(), Migration4To5())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
