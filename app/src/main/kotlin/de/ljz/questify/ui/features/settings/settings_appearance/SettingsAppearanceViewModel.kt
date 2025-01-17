package de.ljz.questify.ui.features.settings.settings_appearance

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsAppearanceViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsAppearanceUiState())
    val uiState: StateFlow<SettingsAppearanceUiState> = _uiState.asStateFlow()
}