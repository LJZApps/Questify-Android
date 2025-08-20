package de.ljz.questify.feature.settings.presentation.screens.appearance

import com.materialkolor.PaletteStyle
import de.ljz.questify.feature.settings.data.models.ThemeBehavior

sealed class SettingsAppearanceUiEvent {
    data object NavigateUp : SettingsAppearanceUiEvent()

    data class UpdateDynamicColorsEnabled(val enabled: Boolean) : SettingsAppearanceUiEvent()

    data object ShowDarkModeDialog : SettingsAppearanceUiEvent()
    data object HideDarkModeDialog : SettingsAppearanceUiEvent()

    data class UpdateIsAmoledEnabled(val enabled: Boolean) : SettingsAppearanceUiEvent()

    data object ShowPaletteStyleDialog : SettingsAppearanceUiEvent()
    data object HidePaletteStyleDialog : SettingsAppearanceUiEvent()

    data object ShowColorPickerDialog : SettingsAppearanceUiEvent()
    data object HideColorPickerDialog : SettingsAppearanceUiEvent()

    data class UpdateThemeBehavior(val behavior: ThemeBehavior) : SettingsAppearanceUiEvent()

    data class UpdatePaletteStyle(val style: PaletteStyle) : SettingsAppearanceUiEvent()

    data class UpdateAppColor(val color: String) : SettingsAppearanceUiEvent()
}