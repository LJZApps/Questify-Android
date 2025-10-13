package de.ljz.questify.core.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.ljz.questify.core.data.database.adapters.DateAdapter
import de.ljz.questify.core.data.database.adapters.HabitTypeConverter
import de.ljz.questify.core.data.database.migrations.InitialMigrationSpec
import de.ljz.questify.core.data.database.migrations.MainQuestAutoMigration
import de.ljz.questify.core.data.database.migrations.QuestEntityAutoMigration
import de.ljz.questify.core.data.database.migrations.QuestTaskAutoMigration
import de.ljz.questify.core.data.database.migrations.RemoveQuestTaskAutoMigration
import de.ljz.questify.feature.habits.data.daos.HabitDao
import de.ljz.questify.feature.habits.data.models.HabitsEntity
import de.ljz.questify.feature.quests.data.daos.QuestCategoryDao
import de.ljz.questify.feature.quests.data.daos.QuestDao
import de.ljz.questify.feature.quests.data.daos.QuestNotificationDao
import de.ljz.questify.feature.quests.data.daos.SubQuestDao
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import de.ljz.questify.feature.quests.data.models.SubQuestEntity

@Database(
    entities = [
        // Quests
        QuestEntity::class,
        QuestNotificationEntity::class,
        QuestCategoryEntity::class,

        // SubQuests
        SubQuestEntity::class,

        // Habits
        HabitsEntity::class
    ],
    version = 9,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = InitialMigrationSpec::class),
        AutoMigration(from = 2, to = 3, spec = MainQuestAutoMigration::class),
        AutoMigration(from = 3, to = 4, spec = QuestEntityAutoMigration::class),
        AutoMigration(from = 4, to = 5, spec = QuestTaskAutoMigration::class),
        AutoMigration(from = 5, to = 6, spec = RemoveQuestTaskAutoMigration::class),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8)
    ]
)
@TypeConverters(
    value = [
        DateAdapter::class,
        HabitTypeConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val questDao: QuestDao
    abstract val questNotificationDao: QuestNotificationDao
    abstract val questCategoryDao: QuestCategoryDao
    abstract val subQuestDao: SubQuestDao
    abstract val habitDao: HabitDao
}