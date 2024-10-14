package de.ljz.questify.ui.features.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.ljz.questify.data.repositories.AppSettingsRepository
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private val _themeBehavior = MutableStateFlow(ThemeBehavior.SYSTEM_STANDARD)
    val themeBehavior: StateFlow<ThemeBehavior> = _themeBehavior.asStateFlow()

    private val _themeColor = MutableStateFlow(ThemeColor.RED)
    val themeColor: StateFlow<ThemeColor> = _themeColor.asStateFlow()

    private val _dynamicColorsEnabled = MutableStateFlow(false)
    val dynamicColorsEnabled: StateFlow<Boolean> = _dynamicColorsEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsRepository.getAppSettings().collectLatest { settings ->
                _themeBehavior.value = settings.themeBehavior
                _themeColor.value = settings.themeColor
                _dynamicColorsEnabled.value = settings.dynamicThemeColors
            }
        }
    }

    /*
    fun updateThemeBehavior(behavior: ThemeBehavior) {
        viewModelScope.launch {
            appSettingsRepository.(behavior)
        }
    }
     */

    /*
    fun updateThemeColor(color: ThemeColor) {
        viewModelScope.launch {
            appSettingsRepository.setThemeColor(color)
        }
    }
     */

    fun updateDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            Log.d("QuestiyTheme", enabled.toString())
            appSettingsRepository.setDynamicColorsEnabled(enabled)
        }
    }
}