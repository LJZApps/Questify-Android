package de.ljz.questify.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.data.datastore.AppSettingsSerializer
import de.ljz.questify.data.datastore.AppUser
import de.ljz.questify.data.datastore.AppUserSerializer
import de.ljz.questify.data.datastore.PushSettings
import de.ljz.questify.data.datastore.PushSettingsSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideUserDatastore(@ApplicationContext context: Context): DataStore<AppUser> = DataStoreFactory.create(
        serializer = AppUserSerializer,
        corruptionHandler = ReplaceFileCorruptionHandler { AppUser() },
        migrations = listOf(),
        scope = CoroutineScope(Dispatchers.IO),
        produceFile = { context.dataStoreFile("app_user.json") }
    )

    @Singleton
    @Provides
    fun provideAppSettingsDatastore(@ApplicationContext context: Context): DataStore<AppSettings> {
        return DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { AppSettings() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { context.dataStoreFile("app_settings.json") }
        )
    }

    @Singleton
    @Provides
    fun providePushSettingsDataStore(@ApplicationContext context: Context): DataStore<PushSettings> {
        return DataStoreFactory.create(
            serializer = PushSettingsSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { PushSettings() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { context.dataStoreFile("push_settings.json") }
        )
    }

}