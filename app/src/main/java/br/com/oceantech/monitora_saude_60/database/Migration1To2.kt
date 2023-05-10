package br.com.oceantech.monitora_saude_60.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE medicamentos ADD COLUMN duracao INTEGER NOT NULL DEFAULT 0")
        database.execSQL("ALTER TABLE medicamentos RENAME TO temp_medicamentos");
        database.execSQL("CREATE TABLE medicamentos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT NOT NULL, dosagem REAL NOT NULL, intervaloDoses INTEGER NOT NULL, dataInicio INTEGER NOT NULL, horarios TEXT NOT NULL, tomou INTEGER NOT NULL, duracao INTEGER NOT NULL)");
        database.execSQL("INSERT INTO medicamentos (id, nome, dosagem, intervaloDoses, dataInicio, horarios, tomou, duracao) SELECT id, nome, dosagem, intervaloDoses, dataInicio, horarios, tomou, duracao FROM temp_medicamentos");
        database.execSQL("DROP TABLE temp_medicamentos");
    }
}
