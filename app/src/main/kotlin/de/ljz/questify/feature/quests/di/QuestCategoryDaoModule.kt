package de.ljz.questify.feature.quests.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestCategoryDaoModule {

    @Singleton
    @Provides
    fun provideQuestCategoryDao(db: AppDatabase) = db.getQuestCategoryDao()
}