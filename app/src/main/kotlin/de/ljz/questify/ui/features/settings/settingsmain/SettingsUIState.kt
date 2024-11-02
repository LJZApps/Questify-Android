package de.ljz.questify.ui.features.settings.settingsmain

import de.ljz.questify.core.application.ReminderTime
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor

data class SettingsUIState(
    val selectedThemeColor: ThemeColor = ThemeColor.RED,
    val selectedDarkMode: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val customColorDialogVisible: Boolean = false,
    val darkModeDialogVisible: Boolean = false,
    val reminderDialogVisible: Boolean = false
)

data class CustomColorItem(
    val text: String = "",
    val color: ThemeColor = ThemeColor.RED
)

data class ThemeItem(
    val text: String = "",
    val behavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD
)

data class ReminderItem(
    val text: String = "",
    val time: ReminderTime = ReminderTime.MIN_10
)