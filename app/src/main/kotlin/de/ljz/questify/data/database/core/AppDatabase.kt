package de.ljz.questify.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.ljz.questify.data.database.core.converters.AppDatabaseConverters
import de.ljz.questify.data.database.daos.QuestDao
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.data.database.models.entities.quests.SubQuestEntity

@Database(
    entities = [
        // Quests
        MainQuestEntity::class,
        SubQuestEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppDatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuestDao(): QuestDao
}