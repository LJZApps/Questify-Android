package de.ljz.questify.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.domain.datastore.AppSettings
import de.ljz.questify.domain.datastore.AppSettingsSerializer
import de.ljz.questify.domain.datastore.AppUser
import de.ljz.questify.domain.datastore.AppUserSerializer
import de.ljz.questify.domain.datastore.QuestMaster
import de.ljz.questify.domain.datastore.QuestMasterSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideUserDatastore(@ApplicationContext context: Context): DataStore<AppUser> =
        DataStoreFactory.create(
            serializer = AppUserSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { AppUser() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File(context.filesDir, "datastore/app_user.json") }
        )

    @Singleton
    @Provides
    fun provideAppSettingsDatastore(@ApplicationContext context: Context): DataStore<AppSettings> =
        DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { AppSettings() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File(context.filesDir, "datastore/app_settings.json") }
        )

    @Singleton
    @Provides
    fun provideQuestMasterDatastore(@ApplicationContext context: Context): DataStore<QuestMaster> =
        DataStoreFactory.create(
            serializer = QuestMasterSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { QuestMaster() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File(context.filesDir, "datastore/quest_master.json") }
        )
}