package de.ljz.questify.core.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create the new sub_quest_entity table
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `sub_quest_entity` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `text` TEXT NOT NULL,
                `is_done` INTEGER NOT NULL,
                `quest_id` INTEGER NOT NULL,
                FOREIGN KEY(`quest_id`) REFERENCES `quest_entity`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
            """.trimIndent()
        )

        // Create index on quest_id
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_sub_quest_entity_quest_id` ON `sub_quest_entity` (`quest_id`)"
        )

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