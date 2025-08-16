package de.ljz.questify.ui.features.settings.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materialkolor.PaletteStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.app.AppSettingsRepository
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsAppearanceViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsAppearanceUiState())
    val uiState: StateFlow<SettingsAppearanceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsRepository.getAppSettings().collectLatest { settings ->
                _uiState.update {
                    it.copy(
                        isAmoled = settings.isAmoled,
                        appColor = settings.appColor,
                        themeBehavior = settings.themeBehavior,
                        themeColor = settings.themeColor,
                        dynamicColorsEnabled = settings.dynamicThemeColors,
                        paletteStyle = settings.themeStyle
                    )
                }
            }
        }
    }

    private fun updateUiState(update: SettingsAppearanceUiState.() -> SettingsAppearanceUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun showCustomColorDialog() = updateUiState { copy(customColorDialogVisible = true) }
    fun hideCustomColorDialog() = updateUiState { copy(customColorDialogVisible = false) }
    fun showDarkModeDialog() = updateUiState { copy(darkModeDialogVisible = true) }
    fun hideDarkModeDialog() = updateUiState { copy(darkModeDialogVisible = false) }
    fun showPaletteStyleDialog() = updateUiState { copy(paletteStyleDialogVisible = true) }
    fun hidePaletteStyleDialog() = updateUiState { copy(paletteStyleDialogVisible = false) }
    fun showColorPickerDialog() = updateUiState { copy(colorPickerDialogVisible = true) }
    fun hideColorPickerDialog() = updateUiState { copy(colorPickerDialogVisible = false) }

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

    fun updatePaletteStyle(style: PaletteStyle) {
        viewModelScope.launch {
            appSettingsRepository.setThemeStyle(style)
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