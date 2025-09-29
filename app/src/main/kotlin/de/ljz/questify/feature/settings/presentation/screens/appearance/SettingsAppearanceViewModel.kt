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
                        themeBehavior = settings.themeBehavior
                    )
                }
            }
        }
    }

    fun onUiEvent(event: SettingsAppearanceUiEvent) {
        when(event) {

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

            is SettingsAppearanceUiEvent.UpdateThemeBehavior -> {
                viewModelScope.launch {
                    appSettingsRepository.setDarkModeBehavior(event.behavior)
                }
            }

            else -> Unit
        }
    }
}