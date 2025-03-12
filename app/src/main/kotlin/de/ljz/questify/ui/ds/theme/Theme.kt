package de.ljz.questify.ui.ds.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import de.ljz.questify.ui.state.ThemeColor

/**
 * Legacy theme implementation - will be deprecated
 * Use GameTheme instead for the new game-inspired design system
 */
@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    vm: ThemeViewModel = hiltViewModel(),
    customThemeColor: ThemeColor? = null,
    content: @Composable () -> Unit
) {
    // Forward to the new GameTheme
    GameTheme(
        darkTheme = darkTheme,
        vm = vm,
        content = content
    )
}
