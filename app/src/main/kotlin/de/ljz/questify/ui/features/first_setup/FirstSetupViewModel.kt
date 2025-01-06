package de.ljz.questify.ui.features.first_setup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FirstSetupViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {
    private var _uiState = MutableStateFlow(FirstSetupUiState())
    val uiState = _uiState.asStateFlow()
}