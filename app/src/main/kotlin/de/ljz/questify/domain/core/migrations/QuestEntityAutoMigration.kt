package de.ljz.questify.domain.core.migrations

import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@DeleteTable.Entries(
    DeleteTable(
        tableName = "sub_quests"
    )
)
@RenameColumn.Entries(
    RenameColumn(
        tableName = "main_quests",
        fromColumnName = "description",
        toColumnName = "notes"
    )
)
@RenameTable.Entries(
    RenameTable(
        fromTableName = "main_quests",
        toTableName = "quest_entity"
    )
)
class QuestEntityAutoMigration : AutoMigrationSpec