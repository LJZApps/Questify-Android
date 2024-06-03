package de.ljz.questify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFFB71C1C), // Hauptfarbe
  secondary = Color.Black, // Sekundäre Farbe
  background = Color.Black, // Hintergrundfarbe
  surface = Color.DarkGray, // Oberflächenfarbe
  onPrimary = Color.White, // Textfarbe auf der Hauptfarbe
  onSecondary = Color.White, // Textfarbe auf der Sekundärfarbe
  onBackground = Color.White, // Textfarbe auf dem Hintergrund
  onSurface = Color.White, // Textfarbe auf der Oberfläche
)

private val LightColorScheme = lightColorScheme(
  primary = Color(0xFFB71C1C), // Hauptfarbe
  secondary = Color.Black, // Sekundäre Farbe
  background = Color.White, // Hintergrundfarbe
  surface = Color.LightGray, // Oberflächenfarbe
  onPrimary = Color.Black, // Textfarbe auf der Hauptfarbe
  onSecondary = Color.Black, // Textfarbe auf der Sekundärfarbe
  onBackground = Color.Black, // Textfarbe auf dem Hintergrund
  onSurface = Color.Black // Textfarbe auf der Oberfläche
)

@Composable
fun QuestifyTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}