package de.ljz.questify.feature.settings.presentation.screens.help

sealed class SettingsHelpUiEvent {
    data object NavigateUp : SettingsHelpUiEvent()

    data class Navigate(val route: Any) : SettingsHelpUiEvent()

    data object ShowOnboarding : SettingsHelpUiEvent()

    data object SendFeedback : SettingsHelpUiEvent()
}