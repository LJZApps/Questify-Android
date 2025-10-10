package de.ljz.questify.feature.quests.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.data.database.AppDatabase
import de.ljz.questify.feature.quests.data.daos.SubQuestDao
import de.ljz.questify.feature.quests.domain.repositories.SubQuestRepository
import de.ljz.questify.feature.quests.domain.repositories.SubQuestRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubQuestModule {

    @Singleton
    @Provides
    fun provideSubQuestDao(db: AppDatabase) = db.getSubQuestDao()

    @Provides
    @Singleton
    fun provideSubQuestRepository(
        subQuestDao: SubQuestDao
    ): SubQuestRepository {
        return SubQuestRepositoryImpl(
            subQuestDao = subQuestDao
        )
    }

}