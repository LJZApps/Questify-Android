package de.ljz.questify.ui.ds.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor

// Funktion zur Anpassung von Farbhelligkeit
fun Color.adjustBrightness(factor: Float): Color {
  return copy(
    red = (red * factor).coerceIn(0f, 1f),
    green = (green * factor).coerceIn(0f, 1f),
    blue = (blue * factor).coerceIn(0f, 1f)
  )
}

// Funktion zur Anpassung der Sättigung
fun Color.adjustSaturation(factor: Float): Color {
  val gray = (red + green + blue) / 3
  return copy(
    red = gray + factor * (red - gray),
    green = gray + factor * (green - gray),
    blue = gray + factor * (blue - gray)
  )
}

// Funktion zur Generierung eines Farbschemas aus einer einzigen Farbe
fun generateCompleteDarkColorScheme(baseColor: Color): ColorScheme {
  val primary = baseColor
  val primaryContainer = primary.adjustBrightness(0.7f)
  val secondary = primary.adjustSaturation(0.8f).adjustBrightness(0.6f)
  val secondaryContainer = secondary.adjustBrightness(0.5f)

  val surface = primary.adjustBrightness(0.2f).copy(alpha = 0.1f)
  val onSurface = if (surface.luminance() > 0.5f) Color.Black else Color.White
  val background = surface.adjustBrightness(0.7f)
  val onBackground = if (background.luminance() > 0.5f) Color.Black else Color.White

  return darkColorScheme(
    primary = primary,
    onPrimary = if (primary.luminance() > 0.5f) Color.Black else Color.White,
    primaryContainer = primaryContainer,
    onPrimaryContainer = if (primaryContainer.luminance() > 0.5f) Color.Black else Color.White,
    secondary = secondary,
    onSecondary = if (secondary.luminance() > 0.5f) Color.Black else Color.White,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = if (secondaryContainer.luminance() > 0.5f) Color.Black else Color.White,
    inversePrimary = ColorDarkTokens.InversePrimary,
    tertiary = ColorDarkTokens.Tertiary,
    onTertiary = ColorDarkTokens.OnTertiary,
    tertiaryContainer = ColorDarkTokens.TertiaryContainer,
    onTertiaryContainer = ColorDarkTokens.OnTertiaryContainer,
    background = ColorDarkTokens.Background,
    onBackground = ColorDarkTokens.OnBackground,
    surface = ColorDarkTokens.Surface,
    onSurface = ColorDarkTokens.OnSurface,
    surfaceVariant = ColorDarkTokens.SurfaceVariant,
    onSurfaceVariant = ColorDarkTokens.OnSurfaceVariant,
    surfaceTint = primary,
    inverseSurface = ColorDarkTokens.InverseSurface,
    inverseOnSurface = ColorDarkTokens.InverseOnSurface,
    error = ColorDarkTokens.Error,
    onError = ColorDarkTokens.OnError,
    errorContainer = ColorDarkTokens.ErrorContainer,
    onErrorContainer = ColorDarkTokens.OnErrorContainer,
    outline = ColorDarkTokens.Outline,
    outlineVariant = ColorDarkTokens.OutlineVariant,
    scrim = ColorDarkTokens.Scrim,
    surfaceBright = ColorDarkTokens.SurfaceBright,
    surfaceContainer = ColorDarkTokens.SurfaceContainer,
    surfaceContainerHigh = ColorDarkTokens.SurfaceContainerHigh,
    surfaceContainerHighest = ColorDarkTokens.SurfaceContainerHighest,
    surfaceContainerLow = ColorDarkTokens.SurfaceContainerLow,
    surfaceContainerLowest = ColorDarkTokens.SurfaceContainerLowest,
    surfaceDim = ColorDarkTokens.SurfaceDim,
  )
}

fun generateCompleteLightColorScheme(baseColor: Color): ColorScheme {
  val primary = baseColor
  val primaryContainer = primary.adjustBrightness(1.3f)
  val secondary = primary.adjustSaturation(0.8f).adjustBrightness(1.2f)
  val secondaryContainer = secondary.adjustBrightness(1.1f)

  val surface = primary.adjustBrightness(1.5f).copy(alpha = 0.1f)
  val onSurface = if (surface.luminance() > 0.5f) Color.Black else Color.White
  val background = surface.adjustBrightness(1.5f)
  val onBackground = if (background.luminance() > 0.5f) Color.Black else Color.White

  return lightColorScheme(
    primary = primary,
    onPrimary = if (primary.luminance() > 0.5f) Color.Black else Color.White,
    primaryContainer = primaryContainer,
    onPrimaryContainer = if (primaryContainer.luminance() > 0.5f) Color.Black else Color.White,
    onSecondary = if (secondary.luminance() > 0.5f) Color.Black else Color.White,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = if (secondaryContainer.luminance() > 0.5f) Color.Black else Color.White,
    inversePrimary = ColorLightTokens.InversePrimary,
    secondary = ColorLightTokens.Secondary,
    tertiary = ColorLightTokens.Tertiary,
    onTertiary = ColorLightTokens.OnTertiary,
    tertiaryContainer = ColorLightTokens.TertiaryContainer,
    onTertiaryContainer = ColorLightTokens.OnTertiaryContainer,
    background = ColorLightTokens.Background,
    onBackground = ColorLightTokens.OnBackground,
    surface = ColorLightTokens.Surface,
    onSurface = ColorLightTokens.OnSurface,
    surfaceVariant = ColorLightTokens.SurfaceVariant,
    onSurfaceVariant = ColorLightTokens.OnSurfaceVariant,
    surfaceTint = primary,
    inverseSurface = ColorLightTokens.InverseSurface,
    inverseOnSurface = ColorLightTokens.InverseOnSurface,
    error = ColorLightTokens.Error,
    onError = ColorLightTokens.OnError,
    errorContainer = ColorLightTokens.ErrorContainer,
    onErrorContainer = ColorLightTokens.OnErrorContainer,
    outline = ColorLightTokens.Outline,
    outlineVariant = ColorLightTokens.OutlineVariant,
    scrim = ColorLightTokens.Scrim,
    surfaceBright = ColorLightTokens.SurfaceBright,
    surfaceContainer = ColorLightTokens.SurfaceContainer,
    surfaceContainerHigh = ColorLightTokens.SurfaceContainerHigh,
    surfaceContainerHighest = ColorLightTokens.SurfaceContainerHighest,
    surfaceContainerLow = ColorLightTokens.SurfaceContainerLow,
    surfaceContainerLowest = ColorLightTokens.SurfaceContainerLowest,
    surfaceDim = ColorLightTokens.SurfaceDim,
  )
}

class DarkTheme {
  companion object {
    val Red = generateCompleteDarkColorScheme(Color(0xFFB71C1C))
    val Green = generateCompleteDarkColorScheme(Color(0xFF388E3C))
    val Blue = generateCompleteDarkColorScheme(Color(0xFF1976D2))
    val Yellow = generateCompleteDarkColorScheme(Color(0xFFFBC02D))
    val Orange = generateCompleteDarkColorScheme(Color(0xFFF57C00))
    val Purple = generateCompleteDarkColorScheme(Color(0xFF8E24AA))

    val PastelRed = generateCompleteDarkColorScheme(Color(0xFFFFC1C1))
    val PastelGreen = generateCompleteDarkColorScheme(Color(0xFFB9E6B9))
    val PastelBlue = generateCompleteDarkColorScheme(Color(0xFFB9D7FF))
    val PastelYellow = generateCompleteDarkColorScheme(Color(0xFFFFF9C4))
    val PastelOrange = generateCompleteDarkColorScheme(Color(0xFFFFE0B2))
    val PastelPurple = generateCompleteDarkColorScheme(Color(0xFFE1BEE7))
  }
}

class LightTheme {
  companion object {
    val Red = generateCompleteLightColorScheme(Color(0xFFB71C1C))
    val Green = generateCompleteLightColorScheme(Color(0xFF388E3C))
    val Blue = generateCompleteLightColorScheme(Color(0xFF1976D2))
    val Yellow = generateCompleteLightColorScheme(Color(0xFFFBC02D))
    val Orange = generateCompleteLightColorScheme(Color(0xFFF57C00))
    val Purple = generateCompleteLightColorScheme(Color(0xFF8E24AA))

    val PastelRed = generateCompleteLightColorScheme(Color(0xFFFFC1C1))
    val PastelGreen = generateCompleteLightColorScheme(Color(0xFFB9E6B9))
    val PastelBlue = generateCompleteLightColorScheme(Color(0xFFB9D7FF))
    val PastelYellow = generateCompleteLightColorScheme(Color(0xFFFFF9C4))
    val PastelOrange = generateCompleteLightColorScheme(Color(0xFFFFE0B2))
    val PastelPurple = generateCompleteLightColorScheme(Color(0xFFE1BEE7))
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
  private val Primary = Color(0xFFB71C1C)
  val OnPrimary = Color.White
  val PrimaryContainer = Color(0xFF370B0B)
  val OnPrimaryContainer = Color(0xFFFFCDD2)
  val InversePrimary = Color(0xFFC2185B)
  val Secondary = Color(0xFF03DAC5)
  val OnSecondary = Color.Black
  val SecondaryContainer = Color(0xFF004D40)
  val OnSecondaryContainer = Color(0xFF80CBC4)
  val Tertiary = Color(0xFFFF5722)
  val OnTertiary = Color.White
  val TertiaryContainer = Color(0xFFBF360C)
  val OnTertiaryContainer = Color(0xFFFFAB91)
  val Background = Color.Black
  val OnBackground = Color.White
  val Surface = Color.Black
  val OnSurface = Color.White
  val SurfaceVariant = Color(0xFF121212)
  val OnSurfaceVariant = Color(0xFFE0E0E0)
  val SurfaceTint = Primary
  val InverseSurface = Color(0xFF303030)
  val InverseOnSurface = Color(0xFFE0E0E0)
  val Error = Color(0xFFCF6679)
  val OnError = Color.Black
  val ErrorContainer = Color(0xFFB00020)
  val OnErrorContainer = Color(0xFFFFCDD2)
  val Outline = Color(0xFFBDBDBD)
  val OutlineVariant = Color(0xFF9E9E9E)
  val Scrim = Color(0xFF000000)
  val SurfaceBright = Color(0xFF424242)
  val SurfaceContainer = Color(0xFF1F1F1F)
  val SurfaceContainerHigh = Color(0xFF2D2D2D)
  val SurfaceContainerHighest = Color(0xFF1B1B1B)
  val SurfaceContainerLow = Color(0xFF161616)
  val SurfaceContainerLowest = Color(0xFF121212)
  val SurfaceDim = Color(0xFF2C2C2C)
}

object ColorLightTokens {
  private val Primary = Color(0xFFB71C1C)
  val OnPrimary = Color.White
  val PrimaryContainer = Color(0xFFFFCDD2)
  val OnPrimaryContainer = Color(0xFF370B0B)
  val InversePrimary = Color(0xFFC2185B)
  val Secondary = Color(0xFF03DAC5)
  val OnSecondary = Color.Black
  val SecondaryContainer = Color(0xFF80CBC4)
  val OnSecondaryContainer = Color(0xFF004D40)
  val Tertiary = Color(0xFFFF5722)
  val OnTertiary = Color.White
  val TertiaryContainer = Color(0xFFFFAB91)
  val OnTertiaryContainer = Color(0xFFBF360C)
  val Background = Color.White
  val OnBackground = Color.Black
  val Surface = Color.White
  val OnSurface = Color.Black
  val SurfaceVariant = Color(0xFFEEEEEE)
  val OnSurfaceVariant = Color(0xFF424242)
  val SurfaceTint = Primary
  val InverseSurface = Color(0xFFE0E0E0)
  val InverseOnSurface = Color(0xFF303030)
  val Error = Color(0xFFB00020)
  val OnError = Color.White
  val ErrorContainer = Color(0xFFFFCDD2)
  val OnErrorContainer = Color(0xFF370B0B)
  val Outline = Color(0xFF757575)
  val OutlineVariant = Color(0xFF9E9E9E)
  val Scrim = Color(0xFF000000)
  val SurfaceBright = Color(0xFFFFFFFF)
  val SurfaceContainer = Color(0xFFF5F5F5)
  val SurfaceContainerHigh = Color(0xFFE0E0E0)
  val SurfaceContainerHighest = Color(0xFFFAFAFA)
  val SurfaceContainerLow = Color(0xFFF8F8F8)
  val SurfaceContainerLowest = Color(0xFFFFFFFF)
  val SurfaceDim = Color(0xFFEEEEEE)
}
