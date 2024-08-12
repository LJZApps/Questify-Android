package de.ljz.questify.ui.features.getstarted.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.navigation.GetStartedNavGraph
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class)
@GetStartedNavGraph
@Destination
@Composable
fun GetStartedChooserScreen(
  modifier: Modifier = Modifier,
  navigator: DestinationsNavigator,
  vm: GetStartedViewModel = hiltViewModel(),
) {
  SentryTraced(tag = "get_started_chooser") {

  }
}