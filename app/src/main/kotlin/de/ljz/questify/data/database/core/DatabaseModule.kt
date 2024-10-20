package de.ljz.questify.data.database.core

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
  fun userDao(db: AppDatabase) = db.getQuestDao()
}