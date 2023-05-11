package br.com.oceantech.monitora_saude_60.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, birthday INTEGER NOT NULL, login TEXT NOT NULL, password TEXT NOT NULL, phone TEXT NOT NULL)")
    }
}
