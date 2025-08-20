package de.ljz.questify.core.data.database.migrations

import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@RenameTable.Entries(
    RenameTable(
        fromTableName = "quest_checklist_entity",
        toTableName = "quest_task_entity"
    )
)
class QuestTaskAutoMigration : AutoMigrationSpec