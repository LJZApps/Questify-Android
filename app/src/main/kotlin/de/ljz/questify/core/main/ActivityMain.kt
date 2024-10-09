package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.getstarted.pages.GetStartedMainScreen
import de.ljz.questify.ui.features.home.HomeScreen
import de.ljz.questify.ui.navigation.GetStartedMain
import de.ljz.questify.ui.navigation.home.Home
import io.sentry.android.core.BuildConfig
import io.sentry.android.core.SentryAndroid
import org.koin.android.ext.android.inject

class ActivityMain : AppCompatActivity() {

  @OptIn(ExperimentalVoyagerApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val splashScreen = installSplashScreen()

    setContent {
      splashScreen.setKeepOnScreenCondition { true }
      val vm: AppViewModel by inject()

      val appUiState by vm.uiState.collectAsState()
      val isSetupDone = appUiState.isSetupDone

      val isAppReadyState by vm.isAppReady.collectAsState()

      SentryAndroid.init(this) { options ->
        options.dsn =
          "https://d98d827f0a668a55c6d7db8c070174e7@o4507245189267456.ingest.de.sentry.io/4507328037191760"
        options.isDebug = BuildConfig.DEBUG

        // Currently under experimental options:
        options.experimental.sessionReplay.errorSampleRate = 1.0
        options.experimental.sessionReplay.sessionSampleRate = 0.1
        options.experimental.sessionReplay.redactAllText = true
        options.experimental.sessionReplay.redactAllImages = true
      }

      if (isAppReadyState) {
        splashScreen.setKeepOnScreenCondition { false }

        QuestifyTheme {
          Surface(
            modifier = Modifier.fillMaxSize()
          ) {
            val navController = rememberNavController()
            NavHost(
              navController = navController,
              startDestination = if (isSetupDone) Home else GetStartedMain
            ) {
              composable<GetStartedMain> {
                GetStartedMainScreen(
                  navController = navController
                )
              }
              composable<Home> {
                HomeScreen(
                  navController = navController
                )
              }
            }
          }
        }
      }
    }

  }
}