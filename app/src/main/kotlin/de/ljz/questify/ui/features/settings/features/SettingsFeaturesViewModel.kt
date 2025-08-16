package de.ljz.questify.ui.features.settings.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.app.FeatureSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFeaturesViewModel @Inject constructor(
    private val featureSettingsRepository: FeatureSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsFeaturesUiState())
    val uiState: StateFlow<SettingsFeaturesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            featureSettingsRepository.getFeatureSettings().collectLatest { featureSettings ->
                updateUiState {
                    copy(
                        questFeaturesState = QuestFeaturesState(
                            fastAddingEnabled = featureSettings.questFastAddingEnabled
                        )
                    )
                }
            }
        }
    }

    private fun updateUiState(update: SettingsFeaturesUiState.() -> SettingsFeaturesUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun updateFastAddingEnabled(enabled: Boolean) {
        viewModelScope.launch {
            featureSettingsRepository.setQuestFastAddingEnabled(enabled)
        }
    }
}