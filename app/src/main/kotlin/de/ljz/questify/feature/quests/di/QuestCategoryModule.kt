package de.ljz.questify.feature.quests.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.data.database.AppDatabase
import de.ljz.questify.feature.quests.data.daos.QuestCategoryDao
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestCategoryRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestCategoryModule {

    @Singleton
    @Provides
    fun provideQuestCategoryDao(db: AppDatabase) = db.questCategoryDao

    @Provides
    @Singleton
    fun provideQuestCategoryRepository(
        questCategoryDao: QuestCategoryDao
    ): QuestCategoryRepository {
        return QuestCategoryRepositoryImpl(
            questCategoryDao = questCategoryDao
        )
    }
}