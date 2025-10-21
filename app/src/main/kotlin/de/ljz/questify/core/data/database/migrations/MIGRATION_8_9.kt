package de.ljz.questify.core.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Logik für die Migration:
        // Setze alle Quests, die vorher die Schwierigkeit 'NONE' hatten, auf 'EASY'.
        db.execSQL(
            "UPDATE quest_entity SET difficulty = 'EASY' WHERE difficulty = 'NONE'"
        )

        // Setze alle Quests, die vorher die Schwierigkeit 'EPIC' hatten, auf 'HARD'.
        db.execSQL(
            "UPDATE quest_entity SET difficulty = 'HARD' WHERE difficulty = 'EPIC'"
        )
    }
}