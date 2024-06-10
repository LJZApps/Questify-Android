package de.ljz.questify.ui.features.setup.pages

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.akinci.androidtemplate.ui.navigation.animations.SlideHorizontallyAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.setup.SetupUiState
import de.ljz.questify.ui.features.setup.SetupViewModel
import de.ljz.questify.ui.navigation.SetupNavGraph
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class)
@SetupNavGraph(start = true)
@Destination(style = SlideHorizontallyAnimation::class)
@Composable
fun SetupAppTheme(
  navigator: DestinationsNavigator,
  modifier: Modifier = Modifier,
  vm: SetupViewModel = hiltViewModel(),
) {
  val setupUiState by vm.uiState.collectAsState()

  QuestifyTheme {
    SentryTraced(tag = "setup_app_theme") {
      SetupAppThemeContent(
        uiState = setupUiState,
        onChangeTheme = { vm.changeAppTheme() },
        modifier = modifier
      )
    }
  }
}

@Composable
private fun SetupAppThemeContent(
  uiState: SetupUiState,
  onChangeTheme: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Button(
    onClick = {
      onChangeTheme()
    }
  ) {
    Text(text = "dfdf")
  }
}