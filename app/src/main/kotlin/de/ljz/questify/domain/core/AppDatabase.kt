package de.ljz.questify.domain.core

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.ljz.questify.domain.core.converters.AppDatabaseConverters
import de.ljz.questify.domain.core.migrations.InitialMigrationSpec
import de.ljz.questify.domain.core.migrations.MainQuestAutoMigration
import de.ljz.questify.domain.daos.QuestDao
import de.ljz.questify.domain.daos.QuestNotificationDao
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.models.quests.MainQuestEntity
import de.ljz.questify.domain.models.quests.SubQuestEntity

@Database(
    entities = [
        // Quests
        MainQuestEntity::class,
        SubQuestEntity::class,

        // Notifications
        QuestNotificationEntity::class
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = InitialMigrationSpec::class),
        AutoMigration(from = 2, to = 3, spec = MainQuestAutoMigration::class),
    ]
)
@TypeConverters(AppDatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuestDao(): QuestDao
    abstract fun getQuestNotificationDao(): QuestNotificationDao
}