package de.ljz.questify.feature.settings.presentation.screens.help

sealed class SettingsHelpUiEvent {
    object NavigateUp : SettingsHelpUiEvent()

    object ShowOnboarding : SettingsHelpUiEvent()

    object OnNavigateToSettingsPermissionRoute: SettingsHelpUiEvent()

    object SendFeedback : SettingsHelpUiEvent()
}