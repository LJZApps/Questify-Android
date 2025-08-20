package de.ljz.questify.feature.settings.presentation.screens.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
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

    fun onUiEvent(event: SettingsAppearanceUiEvent) {
        when(event) {
            is SettingsAppearanceUiEvent.UpdateDynamicColorsEnabled -> {
                viewModelScope.launch {
                    appSettingsRepository.setDynamicColorsEnabled(event.enabled)
                }
            }

            is SettingsAppearanceUiEvent.ShowDarkModeDialog -> {
                _uiState.update {
                    it.copy(
                        darkModeDialogVisible = true
                    )
                }
            }

            is SettingsAppearanceUiEvent.HideDarkModeDialog -> {
                _uiState.update {
                    it.copy(
                        darkModeDialogVisible = false
                    )
                }
            }

            is SettingsAppearanceUiEvent.UpdateIsAmoledEnabled -> {
                viewModelScope.launch {
                    appSettingsRepository.isAmoledEnabled(event.enabled)
                }
            }

            is SettingsAppearanceUiEvent.ShowPaletteStyleDialog -> {
                _uiState.update {
                    it.copy(
                        paletteStyleDialogVisible = true
                    )
                }
            }

            is SettingsAppearanceUiEvent.HidePaletteStyleDialog -> {
                _uiState.update {
                    it.copy(
                        paletteStyleDialogVisible = false
                    )
                }
            }

            is SettingsAppearanceUiEvent.ShowColorPickerDialog -> {
                _uiState.update {
                    it.copy(
                        colorPickerDialogVisible = true
                    )
                }
            }

            is SettingsAppearanceUiEvent.HideColorPickerDialog -> {
                _uiState.update {
                    it.copy(
                        colorPickerDialogVisible = false
                    )
                }
            }

            is SettingsAppearanceUiEvent.UpdateThemeBehavior -> {
                viewModelScope.launch {
                    appSettingsRepository.setDarkModeBehavior(event.behavior)
                }
            }

            is SettingsAppearanceUiEvent.UpdatePaletteStyle -> {
                viewModelScope.launch {
                    appSettingsRepository.setThemeStyle(event.style)
                }
            }

            is SettingsAppearanceUiEvent.UpdateAppColor -> {
                viewModelScope.launch {
                    appSettingsRepository.setAppColor(event.color)
                }
            }

            else -> Unit
        }
    }
}