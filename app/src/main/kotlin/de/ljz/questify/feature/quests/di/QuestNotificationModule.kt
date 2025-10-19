package de.ljz.questify.feature.quests.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.data.database.AppDatabase
import de.ljz.questify.feature.quests.data.daos.QuestNotificationDao
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestNotificationModule {

    @Singleton
    @Provides
    fun provideQuestNotificationDao(db: AppDatabase) = db.questNotificationDao

    @Provides
    @Singleton
    fun provideQuestNotificationRepository(
        questNotificationDao: QuestNotificationDao
    ): QuestNotificationRepository {
        return QuestNotificationRepositoryImpl(
            questNotificationDao = questNotificationDao
        )
    }
}