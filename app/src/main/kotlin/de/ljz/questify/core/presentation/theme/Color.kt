package de.ljz.questify.core.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor

// Utility functions for color adjustments
fun Color.adjustBrightness(factor: Float): Color {
    return copy(
        red = (red * factor).coerceIn(0f, 1f),
        green = (green * factor).coerceIn(0f, 1f),
        blue = (blue * factor).coerceIn(0f, 1f)
    )
}

fun Color.adjustSaturation(factor: Float): Color {
    val gray = (red + green + blue) / 3
    return copy(
        red = gray + factor * (red - gray),
        green = gray + factor * (green - gray),
        blue = gray + factor * (blue - gray)
    )
}

// New modern color palette
object ModernColors {
    // Primary colors
    val Indigo = Color(0xFF3F51B5)
    val Teal = Color(0xFF009688)
    val DeepPurple = Color(0xFF673AB7)
    val Amber = Color(0xFFFFC107)
    val Cyan = Color(0xFF00BCD4)
    val DeepOrange = Color(0xFFFF5722)

    // Pastel colors
    val PastelIndigo = Color(0xFFD1D9FF)
    val PastelTeal = Color(0xFFB2DFDB)
    val PastelPurple = Color(0xFFE1BEE7)
    val PastelAmber = Color(0xFFFFECB3)
    val PastelCyan = Color(0xFFB2EBF2)
    val PastelOrange = Color(0xFFFFCCBC)

    // Neutral colors
    val DarkGray = Color(0xFF121212)
    val MediumGray = Color(0xFF2D2D2D)
    val LightGray = Color(0xFFE0E0E0)
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)

    // Accent colors
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9800)
    val Error = Color(0xFFE91E63)
    val Info = Color(0xFF2196F3)
}

// Function to generate a dark color scheme from a base color
fun generateCompleteDarkColorScheme(baseColor: Color): ColorScheme {
    val primary = baseColor
    val primaryContainer = primary.adjustBrightness(0.7f)
    val secondary = primary.adjustSaturation(0.8f).adjustBrightness(1.2f)
    val secondaryContainer = secondary.adjustBrightness(0.5f)

    return darkColorScheme(
        primary = primary,
        onPrimary = if (primary.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        primaryContainer = primaryContainer,
        onPrimaryContainer = if (primaryContainer.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        secondary = secondary,
        onSecondary = if (secondary.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = if (secondaryContainer.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        tertiary = ModernColors.DeepOrange,
        onTertiary = ModernColors.White,
        tertiaryContainer = ModernColors.DeepOrange.adjustBrightness(0.5f),
        onTertiaryContainer = ModernColors.White,
        background = ModernColors.DarkGray,
        onBackground = ModernColors.White,
        surface = ModernColors.DarkGray,
        onSurface = ModernColors.White,
        surfaceVariant = ModernColors.MediumGray,
        onSurfaceVariant = ModernColors.LightGray,
        surfaceTint = primary,
        inverseSurface = ModernColors.LightGray,
        inverseOnSurface = ModernColors.DarkGray,
        error = ModernColors.Error,
        onError = ModernColors.White,
        errorContainer = ModernColors.Error.adjustBrightness(0.7f),
        onErrorContainer = ModernColors.White,
        outline = ModernColors.LightGray.adjustBrightness(0.7f),
        outlineVariant = ModernColors.LightGray.adjustBrightness(0.5f),
        scrim = ModernColors.Black,
        surfaceBright = ModernColors.MediumGray.adjustBrightness(1.2f),
        surfaceContainer = ModernColors.MediumGray,
        surfaceContainerHigh = ModernColors.MediumGray.adjustBrightness(1.1f),
        surfaceContainerHighest = ModernColors.MediumGray.adjustBrightness(1.2f),
        surfaceContainerLow = ModernColors.MediumGray.adjustBrightness(0.9f),
        surfaceContainerLowest = ModernColors.MediumGray.adjustBrightness(0.8f),
        surfaceDim = ModernColors.MediumGray.adjustBrightness(0.7f),
    )
}

fun generateCompleteLightColorScheme(baseColor: Color): ColorScheme {
    val primary = baseColor
    val primaryContainer = primary.adjustBrightness(1.3f)
    val secondary = primary.adjustSaturation(0.8f).adjustBrightness(1.2f)
    val secondaryContainer = secondary.adjustBrightness(1.1f)

    return lightColorScheme(
        primary = primary,
        onPrimary = if (primary.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        primaryContainer = primaryContainer,
        onPrimaryContainer = if (primaryContainer.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        secondary = secondary,
        onSecondary = if (secondary.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = if (secondaryContainer.luminance() > 0.5f) ModernColors.Black else ModernColors.White,
        tertiary = ModernColors.DeepOrange,
        onTertiary = ModernColors.White,
        tertiaryContainer = ModernColors.PastelOrange,
        onTertiaryContainer = ModernColors.DeepOrange,
        background = ModernColors.White,
        onBackground = ModernColors.Black,
        surface = ModernColors.White,
        onSurface = ModernColors.Black,
        surfaceVariant = ModernColors.LightGray,
        onSurfaceVariant = ModernColors.DarkGray,
        surfaceTint = primary,
        inverseSurface = ModernColors.DarkGray,
        inverseOnSurface = ModernColors.White,
        error = ModernColors.Error,
        onError = ModernColors.White,
        errorContainer = ModernColors.Error.adjustBrightness(1.3f),
        onErrorContainer = ModernColors.Error.adjustBrightness(0.7f),
        outline = ModernColors.MediumGray.adjustBrightness(1.5f),
        outlineVariant = ModernColors.MediumGray.adjustBrightness(1.7f),
        scrim = ModernColors.Black,
        surfaceBright = ModernColors.White,
        surfaceContainer = ModernColors.LightGray.adjustBrightness(1.05f),
        surfaceContainerHigh = ModernColors.LightGray,
        surfaceContainerHighest = ModernColors.LightGray.adjustBrightness(0.95f),
        surfaceContainerLow = ModernColors.LightGray.adjustBrightness(1.1f),
        surfaceContainerLowest = ModernColors.White,
        surfaceDim = ModernColors.LightGray.adjustBrightness(1.05f),
    )
}

class DarkTheme {
    companion object {
        val Red = generateCompleteDarkColorScheme(ModernColors.DeepOrange)
        val Green = generateCompleteDarkColorScheme(ModernColors.Teal)
        val Blue = generateCompleteDarkColorScheme(ModernColors.Indigo)
        val Yellow = generateCompleteDarkColorScheme(ModernColors.Amber)
        val Orange = generateCompleteDarkColorScheme(ModernColors.DeepOrange)
        val Purple = generateCompleteDarkColorScheme(ModernColors.DeepPurple)

        val PastelRed = generateCompleteDarkColorScheme(ModernColors.PastelOrange)
        val PastelGreen = generateCompleteDarkColorScheme(ModernColors.PastelTeal)
        val PastelBlue = generateCompleteDarkColorScheme(ModernColors.PastelIndigo)
        val PastelYellow = generateCompleteDarkColorScheme(ModernColors.PastelAmber)
        val PastelOrange = generateCompleteDarkColorScheme(ModernColors.PastelOrange)
        val PastelPurple = generateCompleteDarkColorScheme(ModernColors.PastelPurple)
    }
}

class LightTheme {
    companion object {
        val Red = generateCompleteLightColorScheme(ModernColors.DeepOrange)
        val Green = generateCompleteLightColorScheme(ModernColors.Teal)
        val Blue = generateCompleteLightColorScheme(ModernColors.Indigo)
        val Yellow = generateCompleteLightColorScheme(ModernColors.Amber)
        val Orange = generateCompleteLightColorScheme(ModernColors.DeepOrange)
        val Purple = generateCompleteLightColorScheme(ModernColors.DeepPurple)

        val PastelRed = generateCompleteLightColorScheme(ModernColors.PastelOrange)
        val PastelGreen = generateCompleteLightColorScheme(ModernColors.PastelTeal)
        val PastelBlue = generateCompleteLightColorScheme(ModernColors.PastelIndigo)
        val PastelYellow = generateCompleteLightColorScheme(ModernColors.PastelAmber)
        val PastelOrange = generateCompleteLightColorScheme(ModernColors.PastelOrange)
        val PastelPurple = generateCompleteLightColorScheme(ModernColors.PastelPurple)
    }
}

fun getColorScheme(
    themeBehavior: ThemeBehavior,
    themeColor: ThemeColor,
    isSystemInDarkTheme: Boolean,
): ColorScheme {
    return when (themeBehavior) {
        ThemeBehavior.DARK -> getDarkColorScheme(themeColor)
        ThemeBehavior.LIGHT -> getLightColorScheme(themeColor)
        ThemeBehavior.SYSTEM_STANDARD -> {
            if (isSystemInDarkTheme) {
                getDarkColorScheme(themeColor)
            } else {
                getLightColorScheme(themeColor)
            }
        }
    }
}

fun getDarkColorScheme(themeColor: ThemeColor): ColorScheme {
    return when (themeColor) {
        ThemeColor.RED -> DarkTheme.Red
        ThemeColor.GREEN -> DarkTheme.Green
        ThemeColor.BLUE -> DarkTheme.Blue
        ThemeColor.YELLOW -> DarkTheme.Yellow
        ThemeColor.ORANGE -> DarkTheme.Orange
        ThemeColor.PURPLE -> DarkTheme.Purple
        ThemeColor.PASTEL_RED -> DarkTheme.PastelRed
        ThemeColor.PASTEL_GREEN -> DarkTheme.PastelGreen
        ThemeColor.PASTEL_BLUE -> DarkTheme.PastelBlue
        ThemeColor.PASTEL_YELLOW -> DarkTheme.PastelYellow
        ThemeColor.PASTEL_ORANGE -> DarkTheme.PastelOrange
        ThemeColor.PASTEL_PURPLE -> DarkTheme.PastelPurple
    }
}

fun getLightColorScheme(themeColor: ThemeColor): ColorScheme {
    return when (themeColor) {
        ThemeColor.RED -> LightTheme.Red
        ThemeColor.GREEN -> LightTheme.Green
        ThemeColor.BLUE -> LightTheme.Blue
        ThemeColor.YELLOW -> LightTheme.Yellow
        ThemeColor.ORANGE -> LightTheme.Orange
        ThemeColor.PURPLE -> LightTheme.Purple
        ThemeColor.PASTEL_RED -> LightTheme.PastelRed
        ThemeColor.PASTEL_GREEN -> LightTheme.PastelGreen
        ThemeColor.PASTEL_BLUE -> LightTheme.PastelBlue
        ThemeColor.PASTEL_YELLOW -> LightTheme.PastelYellow
        ThemeColor.PASTEL_ORANGE -> LightTheme.PastelOrange
        ThemeColor.PASTEL_PURPLE -> LightTheme.PastelPurple
    }
}

object ColorDarkTokens {
    private val Primary = ModernColors.DeepOrange
    val OnPrimary = ModernColors.White
    val PrimaryContainer = ModernColors.DeepOrange.adjustBrightness(0.7f)
    val OnPrimaryContainer = ModernColors.White
    val InversePrimary = ModernColors.DeepPurple
    val Secondary = ModernColors.Teal
    val OnSecondary = ModernColors.Black
    val SecondaryContainer = ModernColors.Teal.adjustBrightness(0.5f)
    val OnSecondaryContainer = ModernColors.White
    val Tertiary = ModernColors.DeepOrange
    val OnTertiary = ModernColors.White
    val TertiaryContainer = ModernColors.DeepOrange.adjustBrightness(0.5f)
    val OnTertiaryContainer = ModernColors.White
    val Background = ModernColors.DarkGray
    val OnBackground = ModernColors.White
    val Surface = ModernColors.DarkGray
    val OnSurface = ModernColors.White
    val SurfaceVariant = ModernColors.MediumGray
    val OnSurfaceVariant = ModernColors.LightGray
    val SurfaceTint = Primary
    val InverseSurface = ModernColors.LightGray
    val InverseOnSurface = ModernColors.DarkGray
    val Error = ModernColors.Error
    val OnError = ModernColors.White
    val ErrorContainer = ModernColors.Error.adjustBrightness(0.7f)
    val OnErrorContainer = ModernColors.White
    val Outline = ModernColors.LightGray.adjustBrightness(0.7f)
    val OutlineVariant = ModernColors.LightGray.adjustBrightness(0.5f)
    val Scrim = ModernColors.Black
    val SurfaceBright = ModernColors.MediumGray.adjustBrightness(1.2f)
    val SurfaceContainer = ModernColors.MediumGray
    val SurfaceContainerHigh = ModernColors.MediumGray.adjustBrightness(1.1f)
    val SurfaceContainerHighest = ModernColors.MediumGray.adjustBrightness(1.2f)
    val SurfaceContainerLow = ModernColors.MediumGray.adjustBrightness(0.9f)
    val SurfaceContainerLowest = ModernColors.MediumGray.adjustBrightness(0.8f)
    val SurfaceDim = ModernColors.MediumGray.adjustBrightness(0.7f)
}

object ColorLightTokens {
    private val Primary = ModernColors.DeepOrange
    val OnPrimary = ModernColors.White
    val PrimaryContainer = ModernColors.PastelOrange
    val OnPrimaryContainer = ModernColors.DeepOrange
    val InversePrimary = ModernColors.DeepPurple
    val Secondary = ModernColors.Teal
    val OnSecondary = ModernColors.White
    val SecondaryContainer = ModernColors.PastelTeal
    val OnSecondaryContainer = ModernColors.Teal
    val Tertiary = ModernColors.DeepOrange
    val OnTertiary = ModernColors.White
    val TertiaryContainer = ModernColors.PastelOrange
    val OnTertiaryContainer = ModernColors.DeepOrange
    val Background = ModernColors.White
    val OnBackground = ModernColors.Black
    val Surface = ModernColors.White
    val OnSurface = ModernColors.Black
    val SurfaceVariant = ModernColors.LightGray
    val OnSurfaceVariant = ModernColors.DarkGray
    val SurfaceTint = Primary
    val InverseSurface = ModernColors.DarkGray
    val InverseOnSurface = ModernColors.White
    val Error = ModernColors.Error
    val OnError = ModernColors.White
    val ErrorContainer = ModernColors.Error.adjustBrightness(1.3f)
    val OnErrorContainer = ModernColors.Error.adjustBrightness(0.7f)
    val Outline = ModernColors.MediumGray.adjustBrightness(1.5f)
    val OutlineVariant = ModernColors.MediumGray.adjustBrightness(1.7f)
    val Scrim = ModernColors.Black
    val SurfaceBright = ModernColors.White
    val SurfaceContainer = ModernColors.LightGray.adjustBrightness(1.05f)
    val SurfaceContainerHigh = ModernColors.LightGray
    val SurfaceContainerHighest = ModernColors.LightGray.adjustBrightness(0.95f)
    val SurfaceContainerLow = ModernColors.LightGray.adjustBrightness(1.1f)
    val SurfaceContainerLowest = ModernColors.White
    val SurfaceDim = ModernColors.LightGray.adjustBrightness(1.05f)
}
