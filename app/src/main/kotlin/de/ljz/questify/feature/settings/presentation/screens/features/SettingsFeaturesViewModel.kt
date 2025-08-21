package de.ljz.questify.feature.settings.presentation.screens.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.settings.domain.repositories.FeatureSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
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
                _uiState.update {
                    it.copy(
                        questFeaturesState = QuestFeaturesState(
                            fastAddingEnabled = featureSettings.questFastAddingEnabled
                        )
                    )
                }
            }
        }
    }

    fun onUiEvent(event: SettingsFeaturesUiEvent) {
        when (event) {
            is SettingsFeaturesUiEvent.UpdateFastAddingEnabled -> {
                viewModelScope.launch {
                    featureSettingsRepository.setQuestFastAddingEnabled(event.enabled)
                }
            }

            else -> Unit
        }
    }
}