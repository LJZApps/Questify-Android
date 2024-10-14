package de.ljz.questify.ui.ds.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    transparentNavBar: Boolean = true,
    vm: ThemeViewModel = koinViewModel(),
    content: @Composable () -> Unit,
) {
    val isDynamicColorEnabled = vm.dynamicColorsEnabled

    var colorScheme = getColorScheme(vm.themeBehavior, vm.themeColor, isSystemInDarkTheme())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        if (isDynamicColorEnabled) {
            colorScheme = if (isSystemInDarkTheme()) dynamicDarkColorScheme(LocalContext.current) else dynamicLightColorScheme(LocalContext.current)
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