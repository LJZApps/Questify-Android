package de.ljz.questify.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.core.coroutine.ContextProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideContextProvider(): ContextProvider = ContextProviderImpl()
}