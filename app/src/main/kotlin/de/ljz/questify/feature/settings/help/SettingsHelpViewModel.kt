package de.ljz.questify.feature.settings.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.domain.repositories.app.AppSettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsHelpViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    fun resetOnboarding() {
        viewModelScope.launch {
            appSettingsRepository.resetOnboarding()
        }
    }
}