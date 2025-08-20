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
import de.ljz.questify.core.data.models.SortingPreferences
import de.ljz.questify.core.data.models.SortingPreferencesSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideSoringPreferencesDatastore(@ApplicationContext context: Context): DataStore<SortingPreferences> =
        DataStoreFactory.create(
            serializer = SortingPreferencesSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { SortingPreferences() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File(context.filesDir, "datastore/sorting_preferences.json") }
        )
}