package de.ljz.questify.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ModelModule {

    /*@Singleton
    @Provides
    fun provideFarmingModel(@ApplicationContext context: Context): FarmingDetector {
        return FarmingDetector(context)
    }

    @Singleton
    @Provides
    fun provideScoringModel(@ApplicationContext context: Context): AIXPScoring {
        return AIXPScoring(context)
    }*/
}