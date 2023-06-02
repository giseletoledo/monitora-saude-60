package br.com.oceantech.monitora_saude_60.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration4To5 : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `responsaveis` " +
                    "(`celular` TEXT PRIMARY KEY NOT NULL, " +
                    "`nome` TEXT NOT NULL)"
        )
    }
}
