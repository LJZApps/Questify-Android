package de.ljz.questify.core.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import com.materialkolor.rememberDynamicColorScheme
import de.ljz.questify.feature.settings.data.models.ThemeBehavior
import de.ljz.questify.feature.settings.data.models.ThemeColor

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    vm: ThemeViewModel = hiltViewModel(),
    customThemeColor: ThemeColor? = null,
    content: @Composable () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    val useDarkTheme = when (uiState.themeBehavior) {
        ThemeBehavior.DARK -> true
        ThemeBehavior.LIGHT -> false
        ThemeBehavior.SYSTEM_STANDARD -> darkTheme
    }

    val colorScheme = if (uiState.dynamicColorsEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        when (uiState.themingEngine) {
            ThemingEngine.V1 -> getColorScheme(uiState.themeBehavior, customThemeColor ?: uiState.themeColor, darkTheme)
            ThemingEngine.V2 -> rememberDynamicColorScheme(
                seedColor = Color(android.graphics.Color.parseColor(uiState.appColor)),
                isDark = useDarkTheme,
                isAmoled = uiState.isAmoled,
                style = uiState.themeStyle,
                contrastLevel = 0.0
            )
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val windowInsetsController = WindowCompat.getInsetsController(window, view)

            windowInsetsController.apply {
                isAppearanceLightStatusBars = !useDarkTheme
                isAppearanceLightNavigationBars = !useDarkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
