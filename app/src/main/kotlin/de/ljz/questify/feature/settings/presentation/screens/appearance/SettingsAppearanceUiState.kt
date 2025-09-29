package de.ljz.questify.feature.settings.presentation.screens.appearance

import de.ljz.questify.feature.settings.data.models.ThemeBehavior

data class SettingsAppearanceUiState(
    val selectedDarkMode: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val darkModeDialogVisible: Boolean = false,
    val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
)

data class ThemeItem(
    val text: String = "",
    val behavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD
)
