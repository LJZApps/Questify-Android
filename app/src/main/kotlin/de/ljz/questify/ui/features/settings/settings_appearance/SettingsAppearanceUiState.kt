package de.ljz.questify.ui.features.settings.settings_appearance

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.toHex
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import de.ljz.questify.util.Standard

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
