package de.ljz.questify.feature.settings.presentation.screens.appearance

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.toHex
import de.ljz.questify.core.state.ThemeBehavior
import de.ljz.questify.core.state.ThemeColor
import de.ljz.questify.core.utils.Standard

data class SettingsAppearanceUiState(
    val selectedThemeColor: ThemeColor = ThemeColor.RED,
    val selectedDarkMode: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val customColorDialogVisible: Boolean = false,
    val darkModeDialogVisible: Boolean = false,
    val paletteStyleDialogVisible: Boolean = false,
    val colorPickerDialogVisible: Boolean = false,
    val isAmoled: Boolean = false,
    val appColor: String = Color.Standard.toHex(),
    val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val themeColor: ThemeColor = ThemeColor.RED,
    val dynamicColorsEnabled: Boolean = false,
    val paletteStyle: PaletteStyle = PaletteStyle.TonalSpot
)

data class ThemeItem(
    val text: String = "",
    val behavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD
)

data class PaletteStyleItem(
    val text: String = "",
    val style: PaletteStyle = PaletteStyle.TonalSpot
)
