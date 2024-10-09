package de.ljz.questify.core.di

import de.ljz.questify.core.main.AppViewModel
import de.ljz.questify.ui.ds.theme.ThemeViewModel
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.home.HomeViewModel
import de.ljz.questify.ui.features.loginandregister.LoginViewModel
import de.ljz.questify.ui.features.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
  viewModelOf(::AppViewModel)
  viewModelOf(::ThemeViewModel)
  viewModelOf(::GetStartedViewModel)
  viewModelOf(::RegisterViewModel)
  viewModelOf(::HomeViewModel)
  viewModelOf(::LoginViewModel)
}