package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.loginandregister.LoginViewModel
import de.ljz.questify.ui.features.register.RegisterViewModel
import de.ljz.questify.ui.navigation.NavGraphs
import de.ljz.questify.ui.navigation.destinations.RegisterScreenDestination
import io.sentry.android.core.SentryAndroid

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

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
        options.isDebug = true

        // Currently under experimental options:
        options.experimental.sessionReplay.errorSampleRate = 1.0
        options.experimental.sessionReplay.sessionSampleRate = 0.1
        options.experimental.sessionReplay.redactAllText = true
        options.experimental.sessionReplay.redactAllImages = true
      }


      QuestifyTheme {
        Surface (
          modifier = Modifier.fillMaxSize(),
        ) {
          DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            dependenciesContainerBuilder = {
              dependency(NavGraphs.getStarted) {
                val parentEntry = remember(navBackStackEntry) {
                  navController.getBackStackEntry(NavGraphs.getStarted.route)
                }
                hiltViewModel<GetStartedViewModel>(parentEntry)
              }
              dependency(NavGraphs.loginAndRegister) {
                val parentEntry = remember(navBackStackEntry) {
                  navController.getBackStackEntry(NavGraphs.loginAndRegister.route)
                }
                hiltViewModel<LoginViewModel>(parentEntry)
              }
              dependency(RegisterScreenDestination) {
                val parentEntry = remember(navBackStackEntry) {
                  navController.getBackStackEntry(RegisterScreenDestination.route)
                }
                hiltViewModel<RegisterViewModel>(parentEntry)
              }
            },
            modifier = Modifier
              .fillMaxSize(),
            //                              Setup done           Setup unfinished
            startRoute =  if (isSetupDone) NavGraphs.home else NavGraphs.getStarted
          )
        }
      }
    }
  }
}