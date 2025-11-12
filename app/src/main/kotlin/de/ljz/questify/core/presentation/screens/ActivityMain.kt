package de.ljz.questify.core.presentation.screens

import android.os.Bundle
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
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.core.presentation.navigation.AppNavKey
import de.ljz.questify.core.presentation.navigation.ScaleTransitionDirection
import de.ljz.questify.core.presentation.navigation.scaleContentTransform
import de.ljz.questify.core.presentation.theme.QuestifyTheme
import de.ljz.questify.core.worker.QuestNotificationWorker
import de.ljz.questify.feature.habits.presentation.screens.create_habit.CreateHabitRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainRoute
import de.ljz.questify.feature.main.presentation.screens.main.MainScreen
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.OnboardingRoute
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.OnboardingScreen
import de.ljz.questify.feature.profile.presentation.screens.edit_profile.EditProfileRoute
import de.ljz.questify.feature.profile.presentation.screens.edit_profile.EditProfileScreen
import de.ljz.questify.feature.profile.presentation.screens.view_profile.ViewProfileRoute
import de.ljz.questify.feature.profile.presentation.screens.view_profile.ViewProfileScreen
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestScreen
import de.ljz.questify.feature.quests.presentation.screens.edit_quest.EditQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.edit_quest.EditQuestScreen
import de.ljz.questify.feature.quests.presentation.screens.quest_detail.QuestDetailRoute
import de.ljz.questify.feature.quests.presentation.screens.quest_detail.QuestDetailScreen
import de.ljz.questify.feature.settings.presentation.screens.appearance.SettingsAppearanceRoute
import de.ljz.questify.feature.settings.presentation.screens.appearance.SettingsAppearanceScreen
import de.ljz.questify.feature.settings.presentation.screens.help.SettingsHelpRoute
import de.ljz.questify.feature.settings.presentation.screens.help.SettingsHelpScreen
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainRoute
import de.ljz.questify.feature.settings.presentation.screens.main.SettingsMainScreen
import de.ljz.questify.feature.settings.presentation.screens.permissions.PermissionsScreen
import de.ljz.questify.feature.settings.presentation.screens.permissions.SettingsPermissionRoute
import io.sentry.android.core.SentryAndroid
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        enableEdgeToEdge()

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

            val workRequest =
                PeriodicWorkRequestBuilder<QuestNotificationWorker>(15, TimeUnit.MINUTES)
                    .build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                uniqueWorkName = "QuestNotificationWorker",
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.UPDATE,
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
                        val startKey: AppNavKey = if (isSetupDone) MainRoute else OnboardingRoute
                        val backStack = rememberNavBackStack(startKey)

                        NavDisplay(
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator()
                            ),
                            backStack = backStack,
                            entryProvider = entryProvider {
                                entry<MainRoute> { key ->
                                    MainScreen(
                                        onNavigateToSettingsPermissionScreen = { backNavigationEnabled ->
                                            backStack.clear()
                                            backStack.add(SettingsPermissionRoute(backNavigationEnabled = backNavigationEnabled))
                                        },
                                        onNavigateToSettingsScreen = {
                                            backStack.add(SettingsMainRoute)
                                        },
                                        onNavigateToCreateQuestScreen = { selectedList ->
                                            backStack.add(
                                                CreateQuestRoute(
                                                    selectedCategoryIndex = selectedList
                                                )
                                            )
                                        },
                                        onNavigateToEditQuestScreen = { id ->
                                            backStack.add(
                                                EditQuestRoute(
                                                    id = id
                                                )
                                            )
                                        },
                                        onNavigateToQuestDetailScreen = { id ->
                                            backStack.add(
                                                QuestDetailRoute(
                                                    id = id
                                                )
                                            )
                                        },
                                        onNavigateToCreateHabitScreen = {
                                            backStack.add(CreateHabitRoute)
                                        }
                                    )
                                }

                                entry<OnboardingRoute> {
                                    OnboardingScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        },
                                        onNavigateToMainScreen = {
                                            backStack.clear()
                                            backStack.add(MainRoute)
                                        }
                                    )
                                }

                                entry<CreateQuestRoute> {
                                    CreateQuestScreen(
                                        onNavigateBack = {
                                            backStack.removeLastOrNull()
                                        }
                                    )
                                }

                                entry<QuestDetailRoute> { key ->
                                    QuestDetailScreen(
                                        questId = key.id,
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        }
                                    )
                                }

                                entry<EditQuestRoute> {
                                    EditQuestScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        }
                                    )
                                }

                                entry<SettingsMainRoute> {
                                    SettingsMainScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        },
                                        onNavigateToViewProfileScreen = {
                                            backStack.add(ViewProfileRoute)
                                        },
                                        onNavigateToSettingsAppearanceScreen = {
                                            backStack.add(SettingsAppearanceRoute)
                                        },
                                        onNavigateToSettingsHelpScreen = {
                                            backStack.add(SettingsHelpRoute)
                                        }
                                    )
                                }

                                entry<SettingsAppearanceRoute> {
                                    SettingsAppearanceScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        }
                                    )
                                }

                                entry<SettingsHelpRoute> {
                                    SettingsHelpScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        },
                                        onNavigateToOnboardingScreen = {
                                            backStack.add(OnboardingRoute)
                                        },
                                        onNavigateToSettingsPermissionScreen = {
                                            backStack.add(SettingsPermissionRoute())
                                        }
                                    )
                                }

                                entry<SettingsPermissionRoute> { key ->
                                    PermissionsScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        },
                                        canNavigateBack = key.backNavigationEnabled
                                    )
                                }

                                entry<ViewProfileRoute> {
                                    ViewProfileScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        },
                                        onNavigateToEditProfileScreen = {
                                            backStack.add(EditProfileRoute)
                                        }
                                    )
                                }

                                entry<EditProfileRoute> {
                                    EditProfileScreen(
                                        onNavigateUp = {
                                            backStack.removeLastOrNull()
                                        }
                                    )
                                }
                            },
                            transitionSpec = {
                                scaleContentTransform(ScaleTransitionDirection.INWARDS)
                            },
                            popTransitionSpec = {
                                scaleContentTransform(ScaleTransitionDirection.OUTWARDS)
                            },
                            predictivePopTransitionSpec = {
                                scaleContentTransform(ScaleTransitionDirection.OUTWARDS)
                            }
                        )
                    }
                }
            }
        }
    }
}