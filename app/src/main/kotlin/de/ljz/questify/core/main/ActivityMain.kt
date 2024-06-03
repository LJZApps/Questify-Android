package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.core.main.MainViewContract.State
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.loginandregister.LoginViewModel
import de.ljz.questify.ui.features.setup.SetupViewModel
import de.ljz.questify.ui.navigation.NavGraphs
import de.ljz.questify.ui.navigation.destinations.ErrorDialogDestination

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val snackbarHostState = remember { SnackbarHostState() }
      val navController = rememberNavController()
      val vm: AppViewModel by viewModels()

      val uiState: androidx.compose.runtime.State<State> = vm.state.collectAsStateWithLifecycle()
      val isSetupDone by uiState.value.isSetupDone.collectAsState(initial = false)
      val isLoggedIn = uiState.value.isLoggedIn

      QuestifyTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
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
              dependency(ErrorDialogDestination) {

              }
            },
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding),
            //                                              Setup/Login done     Login done (no setup)      Not logged in
            startRoute = if (isLoggedIn) if (isSetupDone) NavGraphs.getStarted else NavGraphs.setup else NavGraphs.getStarted
          )
        }
      }
    }
  }
}