package br.com.oceantech.monitora_saude_60.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.oceantech.monitora_saude_60.dao.MedicamentoDao
import br.com.oceantech.monitora_saude_60.database.converters.DateConverter
import br.com.oceantech.monitora_saude_60.database.converters.TimeListConverter
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.repository.MedicamentoRepository


@Database(entities = [Medicamento::class], version = 1, exportSchema = false)
    @TypeConverters(DateConverter::class, TimeListConverter::class)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun medicamentoDao(): MedicamentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medicamento_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
