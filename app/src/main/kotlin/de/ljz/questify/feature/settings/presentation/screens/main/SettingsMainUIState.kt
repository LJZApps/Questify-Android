package de.ljz.questify.feature.settings.presentation.screens.main

import androidx.compose.ui.graphics.Color
import com.materialkolor.ktx.toHex
import de.ljz.questify.feature.settings.domain.models.ThemeBehavior
import de.ljz.questify.feature.settings.domain.models.ThemeColor
import de.ljz.questify.core.utils.Standard

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