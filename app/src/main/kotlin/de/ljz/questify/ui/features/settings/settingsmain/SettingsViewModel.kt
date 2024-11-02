package de.ljz.questify.ui.features.settings.settingsmain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.application.ReminderTime
import de.ljz.questify.domain.repositories.AppSettingsRepository
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUIState())
    val uiState = _uiState.asStateFlow()

    private val _themeBehavior = MutableStateFlow(ThemeBehavior.SYSTEM_STANDARD)
    val themeBehavior: StateFlow<ThemeBehavior> = _themeBehavior.asStateFlow()

    private val _themeColor = MutableStateFlow(ThemeColor.RED)
    val themeColor: StateFlow<ThemeColor> = _themeColor.asStateFlow()

    private val _dynamicColorsEnabled = MutableStateFlow(false)
    val dynamicColorsEnabled: StateFlow<Boolean> = _dynamicColorsEnabled.asStateFlow()

    private val _reminderTime = MutableStateFlow<ReminderTime>(ReminderTime.MIN_5)
    val reminderTime: StateFlow<ReminderTime> = _reminderTime.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsRepository.getAppSettings().collectLatest { settings ->
                _themeBehavior.value = settings.themeBehavior
                _themeColor.value = settings.themeColor
                _dynamicColorsEnabled.value = settings.dynamicThemeColors
                _reminderTime.value = ReminderTime.fromMinutes(settings.reminderTime)
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
    fun showReminderTimeDialog() = updateUiState { copy(reminderDialogVisible = true) }
    fun hideReminderTimeDialog() = updateUiState { copy(reminderDialogVisible = false) }

    fun updateDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.setDynamicColorsEnabled(enabled)
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

    fun updateReminderTime(reminderTime: ReminderTime) {
        viewModelScope.launch {
            appSettingsRepository.setReminderTime(reminderTime)
        }
    }
}