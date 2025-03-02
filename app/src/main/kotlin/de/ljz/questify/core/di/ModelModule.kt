package de.ljz.questify.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.ai.AIXPScoring
import de.ljz.questify.core.ai.FarmingDetector
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModelModule {

    @Singleton
    @Provides
    fun provideFarmingModel(@ApplicationContext context: Context): FarmingDetector {
        return FarmingDetector(context)
    }

    @Singleton
    @Provides
    fun provideScoringModel(@ApplicationContext context: Context): AIXPScoring {
        return AIXPScoring(context)
    }
}