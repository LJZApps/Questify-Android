package de.ljz.questify.core.di

import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.core.coroutine.ContextProviderImpl
import org.koin.dsl.module

val appModule = module {
  single<ContextProvider> { ContextProviderImpl() }
}