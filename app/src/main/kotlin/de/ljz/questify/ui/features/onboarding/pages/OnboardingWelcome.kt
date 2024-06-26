package de.ljz.questify.ui.features.onboarding.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.glance.text.Text
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.ljz.questify.ui.features.onboarding.OnboardingViewModel
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun OnboardingWelcomeScreen(
  navigator: DestinationsNavigator,
  modifier: Modifier = Modifier,
  vm: OnboardingViewModel = hiltViewModel()
) {
  val onboardingUiState by vm.uiState.collectAsState()

  SentryTraced(tag = "onboarding_main") {
    Text(onboardingUiState.guestName)
  }
}