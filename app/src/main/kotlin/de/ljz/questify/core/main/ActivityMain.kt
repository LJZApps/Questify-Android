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
import de.ljz.questify.ui.features.setup.SetupViewModel
import de.ljz.questify.ui.navigation.NavGraphs
import de.ljz.questify.ui.navigation.destinations.RegisterScreenDestination

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
              dependency(NavGraphs.setup) {
                val parentEntry = remember(navBackStackEntry) {
                  navController.getBackStackEntry(NavGraphs.setup.route)
                }
                hiltViewModel<SetupViewModel>(parentEntry)
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
            //                                  Setup done           Setup unfinished
            startRoute =  if (isSetupDone) NavGraphs.setup else NavGraphs.getStarted
          )
        }
      }
    }
  }
}