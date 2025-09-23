package de.ljz.questify.core.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
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
import de.ljz.questify.feature.settings.data.models.ThemeBehavior

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    vm: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    val useDarkTheme = when (uiState.themeBehavior) {
        ThemeBehavior.DARK -> true
        ThemeBehavior.LIGHT -> false
        ThemeBehavior.SYSTEM_STANDARD -> darkTheme
    }

    val colorSchemeV2 = rememberDynamicColorScheme(
        primary = Color(0xFF26828C),
        isDark = useDarkTheme,
        isAmoled = uiState.isAmoled,
        style = PaletteStyle.Neutral
       /* modifyColorScheme = { colorScheme ->
            colorScheme.copy(
                surface = if (useDarkTheme) Color.Black else Color.White
            )
        }*/
    )

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
        colorScheme = colorSchemeV2,
        typography = Typography,
        content = content
    )
}
