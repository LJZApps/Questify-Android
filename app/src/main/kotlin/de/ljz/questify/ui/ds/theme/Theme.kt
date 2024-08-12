package de.ljz.questify.ui.ds.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuestifyTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  content: @Composable () -> Unit,
) {
  val vm: ThemeViewModel = hiltViewModel()

  val colorScheme = getColorScheme(vm.themeBehavior, vm.themeColor, isSystemInDarkTheme())

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
//            (view.context as Activity).window.navigationBarColor = colorScheme.surfaceColorAtElevation(3.dp).toArgb()
      (view.context as Activity).window.navigationBarColor = colorScheme.background.toArgb()
      ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}