package de.ljz.questify.core.main

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.BuildConfig
import de.ljz.questify.core.worker.QuestNotificationWorker
import de.ljz.questify.ui.components.information_bottom_sheets.ChangelogBottomSheet
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.first_setup.FirstSetupScreen
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.get_started.sub_pages.AdventureScreen
import de.ljz.questify.ui.features.get_started.sub_pages.GetStartedChooserScreen
import de.ljz.questify.ui.features.get_started.sub_pages.GetStartedMainScreen
import de.ljz.questify.ui.features.main.MainScreen
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.ui.features.profile.edit_profile.EditProfileScreen
import de.ljz.questify.ui.features.profile.edit_profile.navigation.EditProfileRoute
import de.ljz.questify.ui.features.profile.view_profile.ViewProfileScreen
import de.ljz.questify.ui.features.profile.view_profile.navigation.ProfileRoute
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestScreen
import de.ljz.questify.ui.features.quests.create_quest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.quest_detail.QuestDetailScreen
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.settings.permissions.PermissionsScreen
import de.ljz.questify.ui.features.settings.permissions.PermissionsViewModel
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.ui.features.settings.settings_help.SettingsHelpScreen
import de.ljz.questify.ui.features.settings.settings_help.navigation.SettingsHelp
import de.ljz.questify.ui.features.settings.settings_main.SettingsScreen
import de.ljz.questify.ui.features.settings.settings_main.navigation.Settings
import de.ljz.questify.ui.navigation.AdventureScreenRoute
import de.ljz.questify.ui.navigation.GetStartedChooser
import de.ljz.questify.ui.navigation.GetStartedMain
import de.ljz.questify.ui.navigation.ScaleTransitionDirection
import de.ljz.questify.ui.navigation.scaleIntoContainer
import de.ljz.questify.ui.navigation.scaleOutOfContainer
import de.ljz.questify.util.changelog.parseYamlChangelog
import de.ljz.questify.util.getLastShownVersion
import de.ljz.questify.util.isAlarmPermissionGranted
import de.ljz.questify.util.isNotificationPermissionGranted
import de.ljz.questify.util.isOverlayPermissionGranted
import de.ljz.questify.util.saveCurrentVersion
import io.sentry.android.core.SentryAndroid
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        val permissionsVm: PermissionsViewModel by viewModels()

        val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionsVm.loadPermissionData(this)
        }

        permissionsVm.initializePermissionLauncher(notificationPermissionLauncher)

        setContent {
            splashScreen.setKeepOnScreenCondition { true }
            val vm: AppViewModel by viewModels()

            val appUiState by vm.uiState.collectAsState()
            val isSetupDone = appUiState.isSetupDone
            val allPermissionsGranted: Boolean = (isNotificationPermissionGranted(this) && isOverlayPermissionGranted(this) && isAlarmPermissionGranted(this))
            val isAppReadyState by vm.isAppReady.collectAsState()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                vm.createNotificationChannel(context = context)
            }

            val workRequest =
                PeriodicWorkRequestBuilder<QuestNotificationWorker>(15, TimeUnit.MINUTES)
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
//                            startDestination = if (isSetupDone) MainRoute else GetStartedMain,
                            startDestination = FirstSetupRoute,
                            enterTransition = {
                                scaleIntoContainer()
                            },
                            exitTransition = {
                                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
                            },
                            popEnterTransition = {
                                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
                            },
                            popExitTransition = {
                                scaleOutOfContainer()
                            }
                        ) {
                            composable<FirstSetupRoute> {
                                FirstSetupScreen(navController = navController)
                            }
                            composable<GetStartedMain> {
                                GetStartedMainScreen(navController = navController)
                            }
                            composable<GetStartedChooser> {
                                GetStartedChooserScreen(navController = navController)
                            }
                            composable<MainRoute> {
                                MainScreen(mainNavController = navController)
                            }
                            composable<Settings> {
                                SettingsScreen(mainNavController = navController)
                            }
                            composable<CreateQuest> {
                                CreateQuestScreen(mainNavController = navController)
                            }
                            composable<QuestDetail>(
                                deepLinks = listOf(
                                    navDeepLink<QuestDetail>(basePath = "questify://quest_detail")
                                ),
                            ) { backStackEntry ->
                                QuestDetailScreen(navController = navController)
                            }
                            composable<ProfileRoute> {
                                ViewProfileScreen(navController = navController)
                            }
                            composable<EditProfileRoute> {
                                EditProfileScreen(navController = navController)
                            }
                            composable<SettingsHelp> {
                                SettingsHelpScreen(mainNavController = navController)
                            }
                            composable<SettingsPermissionRoute> { backStackEntry ->
                                val arguments = backStackEntry.toRoute<SettingsPermissionRoute>()
                                PermissionsScreen(
                                    mainNavController = navController,
                                    viewModel = permissionsVm,
                                    canNavigateBack = arguments.canNavigateBack
                                )

                                BackHandler(enabled = !allPermissionsGranted) {

                                }
                            }
                            composable<AdventureScreenRoute> {
                                AdventureScreen()
                            }
                        }

                        DebugOverlay(
                            "DEV",
                            onResetAppUser = {
                                vm.resetAppUserStats()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DebugOverlay(text: String, onResetAppUser: () -> Unit) {
    if (BuildConfig.DEBUG) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.TopEnd
        ) {
            //Button(onClick = onResetAppUser) { Text("Reset App User Stats") }

            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                fontSize = 10.sp
            )
        }
    }
}