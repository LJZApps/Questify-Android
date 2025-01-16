package de.ljz.questify.ui.ds.theme

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.toHex
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import de.ljz.questify.util.Standard

data class ThemeUiState(
    val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,
    val themeColor: ThemeColor = ThemeColor.RED,
    val dynamicColorsEnabled: Boolean = false,
    val themingEngine: ThemingEngine = ThemingEngine.V2,
    val isAmoled: Boolean = false,
    val themeStyle: PaletteStyle = PaletteStyle.TonalSpot,
    val appColor: String = Color.Standard.toHex()
)
