package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.getstarted.pages.GetStartedChooserScreen
import de.ljz.questify.ui.features.getstarted.pages.GetStartedMainScreen
import de.ljz.questify.ui.features.home.HomeScreen
import io.sentry.android.core.BuildConfig
import io.sentry.android.core.SentryAndroid

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

  @OptIn(ExperimentalVoyagerApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val snackbarHostState = remember { SnackbarHostState() }
      val navController = rememberNavController()
      val vm: AppViewModel by viewModels()

      val appUiState by vm.uiState.collectAsState()
      val isSetupDone by appUiState.isSetupDone.collectAsState(initial = false)

      SentryAndroid.init(this) { options ->
        options.dsn = "https://d98d827f0a668a55c6d7db8c070174e7@o4507245189267456.ingest.de.sentry.io/4507328037191760"
        options.isDebug = BuildConfig.DEBUG

        // Currently under experimental options:
        options.experimental.sessionReplay.errorSampleRate = 1.0
        options.experimental.sessionReplay.sessionSampleRate = 0.1
        options.experimental.sessionReplay.redactAllText = true
        options.experimental.sessionReplay.redactAllImages = true
      }

      QuestifyTheme {
        Surface (
          modifier = Modifier.fillMaxSize()
        ) {
          Navigator(
            //            navigate to Home              navigate to GetStarted
            screen = if (isSetupDone) HomeScreen() else GetStartedMainScreen()
          ) { navigator ->
            SlideTransition(
              navigator = navigator,
              disposeScreenAfterTransitionEnd = true
            )
          }
        }
      }
    }
  }
}