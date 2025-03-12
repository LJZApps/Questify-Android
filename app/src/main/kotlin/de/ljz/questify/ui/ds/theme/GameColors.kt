package de.ljz.questify.ui.ds.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Game-inspired color palette for Questify
 * 
 * This color system is designed to evoke fantasy RPG games with:
 * - Rich, saturated colors for primary actions
 * - Magical accent colors for special elements
 * - Parchment-like backgrounds for light theme
 * - Dark, mysterious backgrounds for dark theme
 * - Clear color coding for quest difficulty levels
 */

// Base colors
val EpicPurple = Color(0xFF7E57C2)
val LegendaryOrange = Color(0xFFFF9800)
val HeroicBlue = Color(0xFF42A5F5)
val MysticGreen = Color(0xFF66BB6A)
val AncientGold = Color(0xFFFFD54F)
val DragonRed = Color(0xFFE57373)

// Light theme background colors
val ParchmentLight = Color(0xFFF8F1E3)
val ParchmentMedium = Color(0xFFEDE3D4)
val ParchmentDark = Color(0xFFE6D7C3)

// Dark theme background colors
val MidnightDark = Color(0xFF1A1A2E)
val MidnightMedium = Color(0xFF16213E)
val MidnightLight = Color(0xFF0F3460)

// Difficulty level colors
val EasyGreen = Color(0xFF81C784)
val MediumYellow = Color(0xFFFFD54F)
val HardOrange = Color(0xFFFF8A65)
val EpicPink = Color(0xFFBA68C8)

// Status colors
val QuestComplete = Color(0xFF66BB6A)
val QuestFailed = Color(0xFFE57373)
val QuestActive = Color(0xFF42A5F5)
val QuestInactive = Color(0xFF9E9E9E)

// Light Theme
val GameLightColorScheme = lightColorScheme(
    primary = HeroicBlue,
    onPrimary = Color.White,
    primaryContainer = HeroicBlue.copy(alpha = 0.2f),
    onPrimaryContainer = HeroicBlue,
    
    secondary = MysticGreen,
    onSecondary = Color.White,
    secondaryContainer = MysticGreen.copy(alpha = 0.2f),
    onSecondaryContainer = MysticGreen,
    
    tertiary = EpicPurple,
    onTertiary = Color.White,
    tertiaryContainer = EpicPurple.copy(alpha = 0.2f),
    onTertiaryContainer = EpicPurple,
    
    background = ParchmentLight,
    onBackground = Color(0xFF3E2723),
    surface = ParchmentLight,
    onSurface = Color(0xFF3E2723),
    
    surfaceVariant = ParchmentMedium,
    onSurfaceVariant = Color(0xFF5D4037),
    
    error = DragonRed,
    onError = Color.White,
    errorContainer = DragonRed.copy(alpha = 0.2f),
    onErrorContainer = DragonRed,
    
    outline = Color(0xFF8D6E63),
    outlineVariant = Color(0xFFBCAAA4),
    
    surfaceContainer = ParchmentMedium,
    surfaceContainerHigh = ParchmentDark,
    surfaceContainerHighest = ParchmentDark.copy(alpha = 0.9f),
    surfaceContainerLow = ParchmentLight,
    surfaceContainerLowest = Color.White,
)

// Dark Theme
val GameDarkColorScheme = darkColorScheme(
    primary = HeroicBlue,
    onPrimary = Color.White,
    primaryContainer = HeroicBlue.copy(alpha = 0.3f),
    onPrimaryContainer = Color.White,
    
    secondary = MysticGreen,
    onSecondary = Color.White,
    secondaryContainer = MysticGreen.copy(alpha = 0.3f),
    onSecondaryContainer = Color.White,
    
    tertiary = EpicPurple,
    onTertiary = Color.White,
    tertiaryContainer = EpicPurple.copy(alpha = 0.3f),
    onTertiaryContainer = Color.White,
    
    background = MidnightDark,
    onBackground = Color.White,
    surface = MidnightDark,
    onSurface = Color.White,
    
    surfaceVariant = MidnightMedium,
    onSurfaceVariant = Color(0xFFE0E0E0),
    
    error = DragonRed,
    onError = Color.White,
    errorContainer = DragonRed.copy(alpha = 0.3f),
    onErrorContainer = Color.White,
    
    outline = Color(0xFF9E9E9E),
    outlineVariant = Color(0xFF757575),
    
    surfaceContainer = MidnightMedium,
    surfaceContainerHigh = MidnightLight,
    surfaceContainerHighest = MidnightLight.copy(alpha = 0.9f),
    surfaceContainerLow = MidnightDark,
    surfaceContainerLowest = Color.Black,
)

// Function to get the appropriate color scheme based on dark mode
fun getGameColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) GameDarkColorScheme else GameLightColorScheme
}

// Extension function to get difficulty color
fun ColorScheme.difficultyColor(difficulty: Int): Color {
    return when (difficulty) {
        0 -> onSurfaceVariant.copy(alpha = 0.5f) // None
        1 -> EasyGreen
        2 -> MediumYellow
        3 -> HardOrange
        4 -> EpicPink
        else -> onSurfaceVariant.copy(alpha = 0.5f)
    }
}

// Extension function to get status color
fun ColorScheme.statusColor(completed: Boolean, active: Boolean): Color {
    return when {
        completed -> QuestComplete
        active -> QuestActive
        else -> QuestInactive
    }
}