package de.ljz.questify.feature.player_stats.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.feature.player_stats.data.models.PlayerStats
import de.ljz.questify.feature.player_stats.data.models.PlayerStatsSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerStatsModule {

    @Singleton
    @Provides
    fun providePlayerStatsDatastore(@ApplicationContext context: Context): DataStore<PlayerStats> =
        DataStoreFactory.create(
            serializer = PlayerStatsSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { PlayerStats() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File(context.filesDir, "datastore/player_stats.json") }
        )

}