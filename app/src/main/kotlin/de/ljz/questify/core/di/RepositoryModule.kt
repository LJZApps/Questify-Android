package de.ljz.questify.core.di

import android.util.Log
import androidx.datastore.core.DataStore
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.data.datastore.AppUserDataStore
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.data.repositories.AppUserRepository
import de.ljz.questify.data.repositories.LoginRepository
import de.ljz.questify.data.repositories.PostRepository
import de.ljz.questify.data.repositories.RegisterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get(), get()) }
    single { PostRepository(get()) }
    single { RegisterRepository(get()) }
    single { AppUserRepository(get<AppUserDataStore>()) } // Verwende AppUserDataStore
    single {
        Log.d("KoinModule", "Providing AppSettingsRepository")
        AppSettingsRepository(get<DataStore<AppSettings>>())
    }
}