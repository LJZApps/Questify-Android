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
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import de.ljz.questify.ui.state.ThemeBehavior

@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    transparentNavBar: Boolean = true,
    vm: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val themeBehavior by vm.themeBehavior.collectAsState() // Reactively track theme behavior
    val themeColor by vm.themeColor.collectAsState() // Reactively track theme color
    val dynamicColorsEnabled by vm.dynamicColorsEnabled.collectAsState() // Reactively track dynamic color setting

    var colorScheme = getColorScheme(themeBehavior, themeColor, darkTheme)

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
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()

            if (!transparentNavBar) {
                (view.context as Activity).window.navigationBarColor = colorScheme.surfaceContainer.toArgb()
            } else {
                (view.context as Activity).window.navigationBarColor = colorScheme.background.toArgb()
            }

            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
