package de.ljz.questify.domain.core.migrations

import androidx.room.DeleteTable
import androidx.room.migration.AutoMigrationSpec

@DeleteTable.Entries(
    DeleteTable(
        tableName = "quest_tasks"
    ),
    DeleteTable(
        tableName = "trophies"
    )
)
class RemoveQuestTaskAutoMigration : AutoMigrationSpec