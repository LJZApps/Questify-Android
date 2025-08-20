package de.ljz.questify.feature.settings.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.state.ThemeBehavior
import de.ljz.questify.core.state.ThemeColor
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsRepository.getAppSettings().collectLatest { settings ->
                _uiState.update {
                    it.copy(
                        isAmoled = settings.isAmoled,
                        appColor = settings.appColor,
                        themeBehavior = settings.themeBehavior,
                        themeColor = settings.themeColor,
                        dynamicColorsEnabled = settings.dynamicThemeColors
                    )
                }
            }
        }
    }

    private fun updateUiState(update: SettingsUIState.() -> SettingsUIState) {
        _uiState.value = _uiState.value.update()
    }

    fun showCustomColorDialog() = updateUiState { copy(customColorDialogVisible = true) }
    fun hideCustomColorDialog() = updateUiState { copy(customColorDialogVisible = false) }
    fun showDarkModeDialog() = updateUiState { copy(darkModeDialogVisible = true) }
    fun hideDarkModeDialog() = updateUiState { copy(darkModeDialogVisible = false) }

    fun updateDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.setDynamicColorsEnabled(enabled)
        }
    }

    fun setAppColor(color: String) {
        viewModelScope.launch {
            appSettingsRepository.setAppColor(color)
        }
    }

    fun updateIsAmoledEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.isAmoledEnabled(enabled)
        }
    }

    fun updateCustomColor(color: ThemeColor) {
        viewModelScope.launch {
            appSettingsRepository.setCustomColor(color)
        }
    }

    fun updateThemeBehavior(themeBehavior: ThemeBehavior) {
        viewModelScope.launch {
            appSettingsRepository.setDarkModeBehavior(themeBehavior)
        }
    }
}