package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.createquest.CreateQuestScreen
import de.ljz.questify.ui.features.createquest.navigation.CreateQuest
import de.ljz.questify.ui.features.getstarted.subpages.GetStartedChooserScreen
import de.ljz.questify.ui.features.getstarted.subpages.GetStartedMainScreen
import de.ljz.questify.ui.features.home.HomeScreen
import de.ljz.questify.ui.features.settings.SettingsScreen
import de.ljz.questify.ui.features.settings.navigation.Settings
import de.ljz.questify.ui.navigation.GetStartedChooser
import de.ljz.questify.ui.navigation.GetStartedMain
import de.ljz.questify.ui.navigation.home.Home
import io.sentry.android.core.BuildConfig
import io.sentry.android.core.SentryAndroid

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        setContent {
            splashScreen.setKeepOnScreenCondition { true }
            val vm: AppViewModel by viewModels()

            val appUiState by vm.uiState.collectAsState()
            val isSetupDone = appUiState.isSetupDone

            val isAppReadyState by vm.isAppReady.collectAsState()

            SentryAndroid.init(this) { options ->
                options.dsn = "https://d98d827f0a668a55c6d7db8c070174e7@o4507245189267456.ingest.de.sentry.io/4507328037191760"
                options.isDebug = BuildConfig.DEBUG
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
                                GetStartedMainScreen(navController = navController)
                            }
                            composable<GetStartedChooser> {
                                GetStartedChooserScreen(navController = navController)
                            }
                            composable<Home> {
                                HomeScreen(mainNavController = navController)
                            }
                            composable<Settings> {
                                SettingsScreen(mainNavController = navController)
                            }
                            composable<CreateQuest> {
                                CreateQuestScreen(mainNavController = navController)
                            }
                        }
                    }
                }
            }
        }

    }
}