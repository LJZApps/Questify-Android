package de.ljz.questify.ui.ds.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor


fun createDarkColorScheme(primary: Color): ColorScheme {
  return darkColorScheme(
    primary = primary,
    onPrimary = ColorDarkTokens.OnPrimary,
    primaryContainer = ColorDarkTokens.PrimaryContainer,
    onPrimaryContainer = ColorDarkTokens.OnPrimaryContainer,
    inversePrimary = ColorDarkTokens.InversePrimary,
    secondary = ColorDarkTokens.Secondary,
    onSecondary = ColorDarkTokens.OnSecondary,
    secondaryContainer = ColorDarkTokens.SecondaryContainer,
    onSecondaryContainer = ColorDarkTokens.OnSecondaryContainer,
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

fun createLightColorScheme(primary: Color): ColorScheme {
  return lightColorScheme(
    primary = primary,
    onPrimary = ColorLightTokens.OnPrimary,
    primaryContainer = ColorLightTokens.PrimaryContainer,
    onPrimaryContainer = ColorLightTokens.OnPrimaryContainer,
    inversePrimary = ColorLightTokens.InversePrimary,
    secondary = ColorLightTokens.Secondary,
    onSecondary = ColorLightTokens.OnSecondary,
    secondaryContainer = ColorLightTokens.SecondaryContainer,
    onSecondaryContainer = ColorLightTokens.OnSecondaryContainer,
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
    val Red = createDarkColorScheme(Color(0xFFB71C1C))
    val Green = createDarkColorScheme(Color(0xFF388E3C))
    val Blue = createDarkColorScheme(Color(0xFF1976D2))
    val Yellow = createDarkColorScheme(Color(0xFFFBC02D))
    val Orange = createDarkColorScheme(Color(0xFFF57C00))
    val Purple = createDarkColorScheme(Color(0xFF8E24AA))

    val PastelRed = createDarkColorScheme(Color(0xFFFFC1C1))
    val PastelGreen = createDarkColorScheme(Color(0xFFB9E6B9))
    val PastelBlue = createDarkColorScheme(Color(0xFFB9D7FF))
    val PastelYellow = createDarkColorScheme(Color(0xFFFFF9C4))
    val PastelOrange = createDarkColorScheme(Color(0xFFFFE0B2))
    val PastelPurple = createDarkColorScheme(Color(0xFFE1BEE7))
  }
}

class LightTheme {
  companion object {
    val Red = createLightColorScheme(Color(0xFFB71C1C))
    val Green = createLightColorScheme(Color(0xFF388E3C))
    val Blue = createLightColorScheme(Color(0xFF1976D2))
    val Yellow = createLightColorScheme(Color(0xFFFBC02D))
    val Orange = createLightColorScheme(Color(0xFFF57C00))
    val Purple = createLightColorScheme(Color(0xFF8E24AA))

    val PastelRed = createLightColorScheme(Color(0xFFFFC1C1))
    val PastelGreen = createLightColorScheme(Color(0xFFB9E6B9))
    val PastelBlue = createLightColorScheme(Color(0xFFB9D7FF))
    val PastelYellow = createLightColorScheme(Color(0xFFFFF9C4))
    val PastelOrange = createLightColorScheme(Color(0xFFFFE0B2))
    val PastelPurple = createLightColorScheme(Color(0xFFE1BEE7))
  }
}

fun getColorScheme(themeBehavior: ThemeBehavior, themeColor: ThemeColor, isSystemInDarkTheme: Boolean): ColorScheme {
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
