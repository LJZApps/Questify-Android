package de.ljz.questify.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WearModule {

    /*@Provides
    @Singleton
    fun provideDataClient(
        @ApplicationContext context: Context
    ): DataClient {
        return Wearable.getDataClient(context)
    }*/
}