package de.ljz.questify.feature.settings.presentation.screens.appearance

import de.ljz.questify.feature.settings.data.models.ThemeBehavior

sealed class SettingsAppearanceUiEvent {
    data object NavigateUp : SettingsAppearanceUiEvent()

    data object ShowDarkModeDialog : SettingsAppearanceUiEvent()
    data object HideDarkModeDialog : SettingsAppearanceUiEvent()

    data class UpdateThemeBehavior(val behavior: ThemeBehavior) : SettingsAppearanceUiEvent()
}