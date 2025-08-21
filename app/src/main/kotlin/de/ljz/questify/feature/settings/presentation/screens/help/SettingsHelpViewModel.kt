package de.ljz.questify.feature.settings.presentation.screens.help

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsHelpViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {


}