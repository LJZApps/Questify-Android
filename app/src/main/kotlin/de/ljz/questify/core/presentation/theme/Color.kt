package de.ljz.questify.core.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

// Light & Dark Neutral Palette
val QuestTeal = Color(0xFF3D8F99)
val QuestTealDark = Color(0xFF2C6B73) // Abgedunkelte Variante für `primaryVariant`

// Neutrals & Backgrounds
val QuestBackgroundGray = Color(0xFFF8F9FA) // Haupt-Hintergrund
val QuestSurfaceWhite = Color(0xFFFFFFFF)    // Für Karten, Textfelder etc.
val QuestDisabledGray = Color(0xFFE9ECEF)    // Hintergrund für inaktive Elemente
val QuestDisabledGrayDark = Color(0xFFD8DCDF) // Variante für `secondaryVariant`
val QuestBorderGray = Color(0xFFDEE2E6)      // Für feine Linien

// Text & Icons
val QuestPrimaryText = Color(0xFF212529)     // Für Überschriften und wichtigen Text
val QuestSecondaryText = Color(0xFF6C757D)   // Für Untertitel und Platzhalter

// Functional Colors
val QuestErrorRed = Color(0xFFB00020) // Standard Material Rot für Fehler


// --- DARK THEME PALETTE ---

// Primary Brand Colors
val QuestTealBright = Color(0xFF62C4CF) // Aufgehellte Variante für besseren Kontrast im Dark Mode
val QuestTealBrightDark = Color(0xFF4DB6AC) // Dazugehörige Variante

// Neutrals & Backgrounds
val QuestDarkBackground = Color(0xFF121212) // Standard-Dunkel-Hintergrund
val QuestDarkSurface = Color(0xFF1E1E1E)     // Etwas hellere Oberfläche für Karten

// Text & Icons
val QuestDarkOnSurface = Color(0xFFE0E0E0)   // Heller Text für dunkle Hintergründe
val QuestDarkSecondaryText = Color(0xFF9E9E9E) // Abgedimmter heller Text

// Functional Colors
val QuestErrorRedDark = Color(0xFFCF6679) // Aufgehelltes Rot für Dark Mode


val DarkColorScheme = darkColorScheme(
    primary = QuestTealBright,
    onPrimary = QuestPrimaryText, // Dunkler Text auf hellem Teal-Button

    secondary = QuestDarkSurface, // Im Dark Mode heben sich die inaktiven Toggles nicht so stark ab
    onSecondary = QuestDarkOnSurface,

    background = QuestDarkBackground,
    onBackground = QuestDarkOnSurface,

    surface = QuestDarkSurface,
    onSurface = QuestDarkOnSurface,

    error = QuestErrorRedDark,
    onError = QuestDarkBackground
)

val LightColorScheme = darkColorScheme(
    primary = QuestTeal, // Haupt-Akzentfarbe für Buttons, etc.
    onPrimary = QuestSurfaceWhite, // Text/Icons auf primärer Farbe

    secondary = QuestDisabledGray, // Farbe für weniger wichtige/inaktive Elemente
    onSecondary = QuestPrimaryText, // Text/Icons auf sekundärer Farbe

    background = QuestBackgroundGray, // Der allgemeine App-Hintergrund
    onBackground = QuestPrimaryText, // Text auf dem Hintergrund

    surface = QuestSurfaceWhite, // Farbe für "Oberflächen" wie Karten, Textfelder
    onSurface = QuestPrimaryText, // Text auf diesen Oberflächen

    error = QuestErrorRed, // Farbe für Fehlermeldungen
    onError = QuestSurfaceWhite // Text auf der Fehlerfarbe
)