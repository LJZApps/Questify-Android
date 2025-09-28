package de.ljz.questify.feature.quests.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.data.database.AppDatabase
import de.ljz.questify.feature.quests.data.daos.QuestDao
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestModule {

    @Singleton
    @Provides
    fun provideQuestDao(db: AppDatabase) = db.getQuestDao()

    @Provides
    @Singleton
    fun provideQuestRepository(
        questDao: QuestDao
    ): QuestRepository {
        return QuestRepositoryImpl(
            questDao = questDao
        )
    }
}