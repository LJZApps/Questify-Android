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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import de.ljz.questify.ui.state.ThemeBehavior

/**
 * Game-inspired theme for Questify
 * 
 * This theme composable applies the game-inspired design system to the app.
 * It uses:
 * - Game-inspired colors from GameColors.kt
 * - Fantasy-style typography from GameTypography.kt
 * - Unique shapes from GameShapes.kt
 * 
 * The theme supports:
 * - Light and dark modes
 * - Dynamic colors on Android 12+ (optional)
 * - System theme following
 */

@Composable
fun GameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    vm: ThemeViewModel = hiltViewModel(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    val themeBehavior = uiState.themeBehavior
    
    // Determine if we should use dark theme
    val useDarkTheme = when (themeBehavior) {
        ThemeBehavior.DARK -> true
        ThemeBehavior.LIGHT -> false
        ThemeBehavior.SYSTEM_STANDARD -> darkTheme
    }
    
    // Get the appropriate color scheme
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        else -> getGameColorScheme(useDarkTheme)
    }

    // Apply system bar colors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            
            // Set status bar color
            window.statusBarColor = colorScheme.surface.toArgb()
            
            // Set navigation bar color
            window.navigationBarColor = colorScheme.surfaceContainer.toArgb()
            
            // Set system bar appearance
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !useDarkTheme
                isAppearanceLightNavigationBars = !useDarkTheme
            }
        }
    }

    // Apply the Material theme with our custom design system components
    MaterialTheme(
        colorScheme = colorScheme,
        typography = GameTypography,
        shapes = GameShapes,
        content = content
    )
}

/**
 * Alternative theme with more fantasy-styled shapes
 */
@Composable
fun GameThemeAlternative(
    darkTheme: Boolean = isSystemInDarkTheme(),
    vm: ThemeViewModel = hiltViewModel(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    val themeBehavior = uiState.themeBehavior
    
    // Determine if we should use dark theme
    val useDarkTheme = when (themeBehavior) {
        ThemeBehavior.DARK -> true
        ThemeBehavior.LIGHT -> false
        ThemeBehavior.SYSTEM_STANDARD -> darkTheme
    }
    
    // Get the appropriate color scheme
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        else -> getGameColorScheme(useDarkTheme)
    }

    // Apply system bar colors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            
            // Set status bar color
            window.statusBarColor = colorScheme.surface.toArgb()
            
            // Set navigation bar color
            window.navigationBarColor = colorScheme.surfaceContainer.toArgb()
            
            // Set system bar appearance
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !useDarkTheme
                isAppearanceLightNavigationBars = !useDarkTheme
            }
        }
    }

    // Apply the Material theme with our alternative shapes
    MaterialTheme(
        colorScheme = colorScheme,
        typography = GameTypography,
        shapes = GameShapesAlternative,
        content = content
    )
}