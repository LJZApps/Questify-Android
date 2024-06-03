package de.ljz.questify.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.data.emitter.ErrorEmitter
import de.ljz.questify.data.emitter.NetworkErrorEmitter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmitterModule {

  @Singleton
  @Provides
  fun provideNetworkErrorEmitter(): NetworkErrorEmitter {
    return NetworkErrorEmitter()
  }

  @Singleton
  @Provides
  fun provideErrorEmitter(): ErrorEmitter {
    return ErrorEmitter()
  }

}