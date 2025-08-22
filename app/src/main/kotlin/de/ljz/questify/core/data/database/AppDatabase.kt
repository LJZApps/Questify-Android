package de.ljz.questify.core.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.ljz.questify.core.data.database.adapters.DateAdapter
import de.ljz.questify.core.data.database.migrations.InitialMigrationSpec
import de.ljz.questify.core.data.database.migrations.MainQuestAutoMigration
import de.ljz.questify.core.data.database.migrations.QuestEntityAutoMigration
import de.ljz.questify.core.data.database.migrations.QuestTaskAutoMigration
import de.ljz.questify.core.data.database.migrations.RemoveQuestTaskAutoMigration
import de.ljz.questify.feature.quests.data.daos.QuestCategoryDao
import de.ljz.questify.feature.quests.data.daos.QuestDao
import de.ljz.questify.feature.quests.data.daos.QuestNotificationDao
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity

@Database(
    entities = [
        // Quests
        QuestEntity::class,
        QuestNotificationEntity::class,
        QuestCategoryEntity::class
    ],
    version = 7,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = InitialMigrationSpec::class),
        AutoMigration(from = 2, to = 3, spec = MainQuestAutoMigration::class),
        AutoMigration(from = 3, to = 4, spec = QuestEntityAutoMigration::class),
        AutoMigration(from = 4, to = 5, spec = QuestTaskAutoMigration::class),
        AutoMigration(from = 5, to = 6, spec = RemoveQuestTaskAutoMigration::class),
        AutoMigration(from = 6, to = 7)
    ]
)
@TypeConverters(DateAdapter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuestDao(): QuestDao
    abstract fun getQuestNotificationDao(): QuestNotificationDao
    abstract fun getQuestCategoryDao(): QuestCategoryDao
}