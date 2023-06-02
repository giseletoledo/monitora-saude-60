package br.com.oceantech.monitora_saude_60.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3To4 : Migration(3, 4)  {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE medicamentos RENAME TO temp_medicamentos");
        database.execSQL("CREATE TABLE medicamentos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, dosagem REAL NOT NULL, intervaloDoses INTEGER NOT NULL, dataInicio INTEGER NOT NULL, horarios TEXT NOT NULL, tomou INTEGER NOT NULL, duracao INTEGER NOT NULL)");
        database.execSQL("INSERT INTO medicamentos (id, nome, dosagem, intervaloDoses, dataInicio, horarios, tomou, duracao) SELECT id, nome, dosagem, intervaloDoses, dataInicio, horarios, tomou, duracao FROM temp_medicamentos");
        database.execSQL("DROP TABLE temp_medicamentos");
    }
}