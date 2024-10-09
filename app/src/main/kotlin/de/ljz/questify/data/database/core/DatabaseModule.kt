package de.ljz.questify.data.database.core

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
  single<AppDatabase> {
    Room.databaseBuilder(
      context = androidContext(),
      klass = AppDatabase::class.java,
      name = "questify_db"
    ).build()
  }
}