package de.ljz.questify.domain.core

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.ljz.questify.domain.core.converters.AppDatabaseConverters
import de.ljz.questify.domain.core.migrations.InitialMigrationSpec
import de.ljz.questify.domain.core.migrations.MainQuestAutoMigration
import de.ljz.questify.domain.core.migrations.QuestEntityAutoMigration
import de.ljz.questify.domain.core.migrations.QuestTaskAutoMigration
import de.ljz.questify.domain.core.migrations.RemoveQuestTaskAutoMigration
import de.ljz.questify.domain.daos.quest.QuestDao
import de.ljz.questify.domain.daos.quest.QuestNotificationDao
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.models.quests.QuestEntity

@Database(
    entities = [
        // Quests
        QuestEntity::class,

        // Notifications
        QuestNotificationEntity::class
    ],
    version = 6,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = InitialMigrationSpec::class),
        AutoMigration(from = 2, to = 3, spec = MainQuestAutoMigration::class),
        AutoMigration(from = 3, to = 4, spec = QuestEntityAutoMigration::class),
        AutoMigration(from = 4, to = 5, spec = QuestTaskAutoMigration::class),
        AutoMigration(from = 5, to = 6, spec = RemoveQuestTaskAutoMigration::class)
    ]
)
@TypeConverters(AppDatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuestDao(): QuestDao
    abstract fun getQuestNotificationDao(): QuestNotificationDao
}