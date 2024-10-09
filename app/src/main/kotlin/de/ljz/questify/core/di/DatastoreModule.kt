package de.ljz.questify.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.data.datastore.AppSettingsSerializer
import de.ljz.questify.data.datastore.AppUser
import de.ljz.questify.data.datastore.AppUserSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val datastoreModule = module {

  single<DataStore<AppUser>> {
    DataStoreFactory.create(
      serializer = AppUserSerializer,
      corruptionHandler = ReplaceFileCorruptionHandler { AppUser() },
      migrations = listOf(),
      scope = CoroutineScope(Dispatchers.IO),
      produceFile = { File(androidContext().filesDir, "datastore/app_user.json") }
    )
  }

  single<DataStore<AppSettings>> {
    DataStoreFactory.create(
      serializer = AppSettingsSerializer,
      corruptionHandler = ReplaceFileCorruptionHandler { AppSettings() },
      migrations = listOf(),
      scope = CoroutineScope(Dispatchers.IO),
      produceFile = { File(androidContext().filesDir, "datastore/app_settings.json") }
    )
  }
}
