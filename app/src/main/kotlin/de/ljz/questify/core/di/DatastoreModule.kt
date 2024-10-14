package de.ljz.questify.core.di

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.data.datastore.AppSettingsSerializer
import de.ljz.questify.data.datastore.AppUserDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val datastoreModule = module {
    single { AppUserDataStore(androidContext()) } // Registriere die AppUserDataStore-Klasse

    single<DataStore<AppSettings>> {
        Log.d("KoinModule", "Providing AppSettings")

        DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { AppSettings() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { File(androidContext().filesDir, "datastore/app_settings.json") }
        )
    }
}
