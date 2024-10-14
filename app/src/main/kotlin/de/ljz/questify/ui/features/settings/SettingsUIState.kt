package de.ljz.questify.ui.features.settings

import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor

data class SettingsUIState(
    val themeColor: ThemeColor = ThemeColor.RED,
    val theme: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val dynamicColorsEnabled: Boolean = false
)
