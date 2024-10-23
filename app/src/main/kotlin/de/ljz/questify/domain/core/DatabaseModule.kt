package de.ljz.questify.domain.core

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(
      context = context,
      klass = AppDatabase::class.java,
      name = "questify_db"
    ).build()
  }
}

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

  @Singleton
  @Provides
  fun questDao(db: AppDatabase) = db.getQuestDao()

  @Singleton
  @Provides
  fun questNotificationDao(db: AppDatabase) = db.getQuestNotificationDao()

}