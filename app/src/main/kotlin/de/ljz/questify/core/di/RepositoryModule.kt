package de.ljz.questify.core.di

import androidx.datastore.core.DataStore
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.data.repositories.LoginRepository
import de.ljz.questify.data.repositories.PostRepository
import de.ljz.questify.data.repositories.RegisterRepository
import org.koin.dsl.module

val repositoryModule = module {
  single<LoginRepository> { LoginRepository(get(), get()) }
  single<PostRepository> { PostRepository(get()) }
  single<RegisterRepository> { RegisterRepository(get()) }
  single<AppSettingsRepository> { AppSettingsRepository(get<DataStore<AppSettings>>()) }
}