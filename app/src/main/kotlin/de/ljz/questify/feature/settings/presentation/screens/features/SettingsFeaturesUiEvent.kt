package de.ljz.questify.feature.settings.presentation.screens.features

sealed class SettingsFeaturesUiEvent {
    data object NavigateUp : SettingsFeaturesUiEvent()

    data class UpdateFastAddingEnabled(val enabled: Boolean) : SettingsFeaturesUiEvent()
}