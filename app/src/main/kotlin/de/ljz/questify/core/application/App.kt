package de.ljz.questify.core.application

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import de.ljz.questify.core.di.appModule
import de.ljz.questify.core.di.datastoreModule
import de.ljz.questify.core.di.emitterModule
import de.ljz.questify.core.di.networkModule
import de.ljz.questify.core.di.repositoryModule
import de.ljz.questify.core.di.viewModelModule
import de.ljz.questify.data.database.core.databaseModule
import de.ljz.questify.data.datastore.AppUser
import org.koin.android.ext.koin.androidContext
import org.koin.compose.getKoin
import org.koin.core.context.startKoin

class App : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@App)
      modules(
        appModule,
        datastoreModule,
        networkModule,
        emitterModule,
        viewModelModule,
        databaseModule,
        repositoryModule
      )
    }
  }
}