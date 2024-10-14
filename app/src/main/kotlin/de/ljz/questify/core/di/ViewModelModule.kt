package de.ljz.questify.core.di

import de.ljz.questify.core.main.AppViewModel
import de.ljz.questify.data.repositories.AppUserRepository
import de.ljz.questify.ui.ds.theme.ThemeViewModel
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.home.HomeViewModel
import de.ljz.questify.ui.features.loginandregister.LoginViewModel
import de.ljz.questify.ui.features.quests.QuestsViewModel
import de.ljz.questify.ui.features.register.RegisterViewModel
import de.ljz.questify.ui.features.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::GetStartedViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModel { QuestsViewModel(get<AppUserRepository>()) }
    viewModelOf(::SettingsViewModel)
}