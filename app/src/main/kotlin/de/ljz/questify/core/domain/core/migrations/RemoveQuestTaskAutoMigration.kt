package de.ljz.questify.core.domain.core.migrations

import androidx.room.DeleteTable
import androidx.room.migration.AutoMigrationSpec

@DeleteTable.Entries(
    DeleteTable(
        tableName = "quest_task_entity"
    ),
    DeleteTable(
        tableName = "trophy_entity"
    ),
    DeleteTable(
        tableName = "trophy_category_entity"
    )
)
class RemoveQuestTaskAutoMigration : AutoMigrationSpec