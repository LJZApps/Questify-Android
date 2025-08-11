package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.core.worker.QuestNotificationWorker
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.ui.navigation.ScaleTransitionDirection
import de.ljz.questify.ui.navigation.authenticationRoutes
import de.ljz.questify.ui.navigation.routes.dialogRoutes
import de.ljz.questify.ui.navigation.routes.mainRoutes
import de.ljz.questify.ui.navigation.routes.profileRoutes
import de.ljz.questify.ui.navigation.routes.questRoutes
import de.ljz.questify.ui.navigation.routes.settingRoutes
import de.ljz.questify.ui.navigation.scaleIntoContainer
import de.ljz.questify.ui.navigation.scaleOutOfContainer
import io.sentry.android.core.SentryAndroid
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )

        window.isNavigationBarContrastEnforced = false

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            splashScreen.setKeepOnScreenCondition { true }
            val vm: AppViewModel by viewModels()

            val appUiState by vm.uiState.collectAsState()
            val isSetupDone = appUiState.isSetupDone
            val isAppReadyState by vm.isAppReady.collectAsState()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                vm.createNotificationChannel(context = context)
            }

            val workRequest = PeriodicWorkRequestBuilder<QuestNotificationWorker>(15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                uniqueWorkName = "QuestNotificationWorker",
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE,
                request = workRequest
            )

            SentryAndroid.init(this) { options ->
                options.dsn =
                    "https://d98d827f0a668a55c6d7db8c070174e7@o4507245189267456.ingest.de.sentry.io/4507328037191760"
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
                            startDestination = if (isSetupDone) MainRoute else FirstSetupRoute,
                            enterTransition = { scaleIntoContainer() },
                            exitTransition = {
                                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
                            },
                            popEnterTransition = {
                                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
                            },
                            popExitTransition = { scaleOutOfContainer() }
                        ) {
                            mainRoutes(
                                navController = navController
                            )

                            dialogRoutes(
                                navController = navController,
                            )

                            settingRoutes(
                                navController = navController
                            )

                            profileRoutes(
                                navController = navController
                            )

                            questRoutes(
                                navController = navController
                            )

                            authenticationRoutes(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}