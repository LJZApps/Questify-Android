package de.ljz.questify.feature.profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.ljz.questify.feature.profile.data.models.AppUser
import de.ljz.questify.feature.profile.data.models.AppUserSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppUserModule {

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

}