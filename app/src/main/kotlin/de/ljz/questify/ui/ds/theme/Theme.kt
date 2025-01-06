package de.ljz.questify.ui.ds.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
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
    val themeBehavior by vm.themeBehavior.collectAsState() // Reactively track theme behavior
    val themeColor by vm.themeColor.collectAsState() // Reactively track theme color
    val dynamicColorsEnabled by vm.dynamicColorsEnabled.collectAsState() // Reactively track dynamic color setting

    val transparentNavBarState = NavBarConfig.transparentNavBar

    var colorScheme = getColorScheme(themeBehavior, customThemeColor?: themeColor, darkTheme)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && dynamicColorsEnabled) {
        colorScheme = when (themeBehavior) {
            ThemeBehavior.DARK -> dynamicDarkColorScheme(LocalContext.current)
            ThemeBehavior.LIGHT -> dynamicLightColorScheme(LocalContext.current)
            ThemeBehavior.SYSTEM_STANDARD -> {
                if (darkTheme) {
                    dynamicDarkColorScheme(LocalContext.current)
                } else {
                    dynamicLightColorScheme(LocalContext.current)
                }
            }
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val windowInsetsController = WindowCompat.getInsetsController(window, view)

            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = if (!transparentNavBarState) {
                colorScheme.surfaceContainer.toArgb()
            } else {
                colorScheme.background.toArgb()
            }

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
