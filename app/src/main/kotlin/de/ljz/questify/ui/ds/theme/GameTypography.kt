package de.ljz.questify.ui.ds.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.ljz.questify.R

/**
 * Game-inspired typography for Questify
 * 
 * This typography system is designed to evoke fantasy RPG games with:
 * - Decorative headings for titles and headers
 * - Clean, readable body text
 * - Stylized labels for game elements
 * - Distinctive quest titles and descriptions
 * 
 * Note: The actual font resources need to be added to the res/font directory.
 * For this implementation, we're using placeholder font references that should be replaced
 * with actual font files.
 */

// Define font families using existing game-inspired fonts
val QuestTitleFont = FontFamily(
    Font(R.font.arcade)
)

val GameBodyFont = FontFamily(
    Font(R.font.vt323_regular)
)

// Secondary fonts for different UI elements
val PixelFont = FontFamily(
    Font(R.font.pressstart_regular)
)

val AltPixelFont = FontFamily(
    Font(R.font.tine5_regular)
)

// Game-inspired typography
val GameTypography = Typography(
    // Large titles for main screens
    displayLarge = TextStyle(
        fontFamily = QuestTitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.5).sp
    ),

    // Medium titles for section headers
    displayMedium = TextStyle(
        fontFamily = QuestTitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),

    // Small titles for card headers
    displaySmall = TextStyle(
        fontFamily = QuestTitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Quest titles
    headlineLarge = TextStyle(
        fontFamily = QuestTitleFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Section headers
    headlineMedium = TextStyle(
        fontFamily = QuestTitleFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),

    // Card titles
    headlineSmall = TextStyle(
        fontFamily = QuestTitleFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    ),

    // Quest descriptions
    titleLarge = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    // Button text
    titleMedium = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    ),

    // Small labels
    titleSmall = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Body text
    bodyLarge = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Secondary text
    bodyMedium = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    // Small text
    bodySmall = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // Labels
    labelLarge = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Small labels
    labelMedium = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    // Tiny labels
    labelSmall = TextStyle(
        fontFamily = GameBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)
