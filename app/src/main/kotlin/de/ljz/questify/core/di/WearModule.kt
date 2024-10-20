package de.ljz.questify.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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