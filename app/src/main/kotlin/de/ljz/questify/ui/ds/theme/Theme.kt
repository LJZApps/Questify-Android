package de.ljz.questify.ui.ds.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicColorScheme
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import de.ljz.questify.util.NavBarConfig

@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    vm: ThemeViewModel = hiltViewModel(),
    customThemeColor: ThemeColor? = null,
    content: @Composable () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    val themeBehavior = uiState.themeBehavior // Reactively track theme behavior
    val themeColor = uiState.themeColor // Reactively track theme color
    val dynamicColorsEnabled = uiState.dynamicColorsEnabled // Reactively track dynamic color setting

    val transparentNavBarState = NavBarConfig.transparentNavBar

    var colorScheme = when (uiState.themingEngine) {
        // FIXME V1 will no longer be updated and will be removed in a future update
        ThemingEngine.V1 -> getColorScheme(themeBehavior, customThemeColor?: themeColor, darkTheme)
        ThemingEngine.V2 -> {
            rememberDynamicColorScheme(
                seedColor = Color(android.graphics.Color.parseColor(uiState.appColor)),
                isDark = when (uiState.themeBehavior) {
                    ThemeBehavior.DARK -> true
                    ThemeBehavior.LIGHT -> false
                    ThemeBehavior.SYSTEM_STANDARD -> isSystemInDarkTheme()
                },
                isAmoled = uiState.isAmoled,
                style = PaletteStyle.TonalSpot,
                contrastLevel = 0.0
            )
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && dynamicColorsEnabled) {
        colorScheme = when (themeBehavior) {
            ThemeBehavior.DARK -> dynamicDarkColorScheme(context)
            ThemeBehavior.LIGHT -> dynamicLightColorScheme(context)
            ThemeBehavior.SYSTEM_STANDARD -> {
                if (darkTheme) {
                    dynamicDarkColorScheme(context)
                } else {
                    dynamicLightColorScheme(context)
                }
            }
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val windowInsetsController = WindowCompat.getInsetsController(window, view)

            /*window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = if (!transparentNavBarState) {
                colorScheme.surfaceContainer.toArgb()
            } else {
                colorScheme.background.toArgb()
            }*/

            // Set system bar appearance
            windowInsetsController.apply {
                isAppearanceLightStatusBars = when (themeBehavior) {
                    ThemeBehavior.DARK -> false
                    ThemeBehavior.LIGHT -> true
                    ThemeBehavior.SYSTEM_STANDARD -> !darkTheme
                }

                isAppearanceLightNavigationBars = when (themeBehavior) {
                    ThemeBehavior.DARK -> false
                    ThemeBehavior.LIGHT -> true
                    ThemeBehavior.SYSTEM_STANDARD -> !darkTheme
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
