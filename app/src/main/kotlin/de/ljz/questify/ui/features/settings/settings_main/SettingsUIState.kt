package de.ljz.questify.ui.features.settings.settings_main

import androidx.compose.ui.graphics.Color
import com.materialkolor.ktx.toHex
import de.ljz.questify.core.application.ReminderTime
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import de.ljz.questify.util.Standard

data class SettingsUIState(
    val selectedThemeColor: ThemeColor = ThemeColor.RED,
    val selectedDarkMode: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val customColorDialogVisible: Boolean = false,
    val darkModeDialogVisible: Boolean = false,
    val reminderDialogVisible: Boolean = false,
    val isAmoled: Boolean = false,
    val appColor: String = Color.Standard.toHex(),
    val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val themeColor: ThemeColor = ThemeColor.RED,
    val dynamicColorsEnabled: Boolean = false
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