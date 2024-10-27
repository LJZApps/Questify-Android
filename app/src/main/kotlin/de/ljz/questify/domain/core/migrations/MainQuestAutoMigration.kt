package de.ljz.questify.domain.core.migrations

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn.Entries(
    DeleteColumn(
        tableName = "main_quests",
        columnName = "points"
    )
)
@RenameColumn.Entries(
    RenameColumn(
        tableName = "main_quests",
        fromColumnName = "points",
        toColumnName = "difficulty"
    )
)
class MainQuestAutoMigration : AutoMigrationSpec