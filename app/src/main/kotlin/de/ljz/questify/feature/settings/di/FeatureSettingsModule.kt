package de.ljz.questify.feature.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.feature.settings.domain.models.FeatureSettings
import de.ljz.questify.feature.settings.domain.models.FeatureSettingsSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureSettingsModule {

    @Singleton
    @Provides
    fun provideFeatureSettingsDatastore(@ApplicationContext context: Context): DataStore<FeatureSettings> =
        DataStoreFactory.create(
            serializer = FeatureSettingsSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { FeatureSettings() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File(context.filesDir, "datastore/feature_settings.json") }
        )

}