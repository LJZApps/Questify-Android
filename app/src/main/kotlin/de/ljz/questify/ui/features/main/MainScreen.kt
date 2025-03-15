package de.ljz.questify.ui.features.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.R
import de.ljz.questify.ui.components.information_bottom_sheets.TutorialBottomSheet
import de.ljz.questify.ui.components.information_bottom_sheets.TutorialStep
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.dashboard.DashboardScreen
import de.ljz.questify.ui.features.dashboard.navigation.DashboardRoute
import de.ljz.questify.ui.features.main.components.DrawerContent
import de.ljz.questify.ui.features.quests.quests_overview.QuestOverviewScreen
import de.ljz.questify.ui.features.quests.quests_overview.navigation.Quests
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.ui.features.trophies.TrophiesOverviewScreen
import de.ljz.questify.ui.features.trophies.navigation.TrophiesRoute
import de.ljz.questify.ui.navigation.ScaleTransitionDirection
import de.ljz.questify.ui.navigation.scaleIntoContainer
import de.ljz.questify.ui.navigation.scaleOutOfContainer
import de.ljz.questify.util.isAlarmPermissionGranted
import de.ljz.questify.util.isNotificationPermissionGranted
import de.ljz.questify.util.isOverlayPermissionGranted
import io.sentry.compose.SentryTraced


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    mainNavController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    val allPermissionsGranted = remember {
        isNotificationPermissionGranted(context) &&
                isOverlayPermissionGranted(context) &&
                isAlarmPermissionGranted(context)
    }

    LaunchedEffect(allPermissionsGranted) {
        if (!allPermissionsGranted) {
            mainNavController.navigate(SettingsPermissionRoute(canNavigateBack = false)) {
                popUpTo(mainNavController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    if (!allPermissionsGranted) return

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val homeNavHostController = rememberNavController()

    QuestifyTheme {
        SentryTraced(tag = "home_screen") {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        uiState = uiState,
                        navController = homeNavHostController,
                        mainNavController = mainNavController,
                        drawerState = drawerState
                    )
                },
                gesturesEnabled = allPermissionsGranted
            ) {
                NavHost(
                    navController = homeNavHostController,
                    startDestination = DashboardRoute,
                    enterTransition = { scaleIntoContainer() },
                    exitTransition = { scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS) },
                    popEnterTransition = { scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS) },
                    popExitTransition = { scaleOutOfContainer() }
                ) {
                    composable<DashboardRoute> {
                        DashboardScreen(mainNavController, drawerState)

                        if (!uiState.tutorialsUiState.dashboardOnboardingDone && uiState.tutorialsUiState.tutorialsEnabled) {
                            TutorialBottomSheet(
                                onDismiss = { tutorialsEnabled ->
                                    viewModel.setDashboardOnboardingDone()
                                    viewModel.toggleTutorials(tutorialsEnabled)
                                },
                                title = stringResource(R.string.tutorial_dashboard_title),
                                tutorialSteps = listOf(
                                    TutorialStep(
                                        icon = Icons.Default.Dashboard,
                                        description = stringResource(R.string.tutorial_dashboard_text_1)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.PieChart,
                                        description = stringResource(R.string.tutorial_dashboard_text_2)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.Settings,
                                        description = stringResource(R.string.tutorial_dashboard_text_3)
                                    )
                                )
                            )
                        }
                    }

                    composable<Quests> {
                        QuestOverviewScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController
                        )

                        if (!uiState.tutorialsUiState.questsOnboardingDone && uiState.tutorialsUiState.tutorialsEnabled) {
                            TutorialBottomSheet(
                                title = stringResource(R.string.tutorial_quests_title),
                                onDismiss = { tutorialsEnabled ->
                                    viewModel.setQuestOnboardingDone()
                                    viewModel.toggleTutorials(tutorialsEnabled)
                                },
                                tutorialSteps = listOf(
                                    TutorialStep(
                                        icon = Icons.AutoMirrored.Filled.List,
                                        description = stringResource(R.string.tutorial_quests_text_1)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Filled.CalendarMonth,
                                        description = stringResource(R.string.tutorial_quests_text_2)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Filled.Schedule,
                                        description = stringResource(R.string.tutorial_quests_text_3)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Filled.Eco,
                                        description = stringResource(R.string.tutorial_quests_text_4)
                                    )
                                )
                            )
                        }
                    }

                    composable<TrophiesRoute> {
                        TrophiesOverviewScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController
                        )

                        if (!uiState.tutorialsUiState.trophiesOnboardingDone && uiState.tutorialsUiState.tutorialsEnabled) {
                            TutorialBottomSheet(
                                onDismiss = { tutorialsEnabled ->
                                    viewModel.setTrophiesOnboardingDone()
                                    viewModel.toggleTutorials(tutorialsEnabled)
                                },
                                title = stringResource(R.string.tutorial_trophies_title),
                                tutorialSteps = listOf(
                                    TutorialStep(
                                        icon = Icons.Default.EmojiEvents,
                                        description = stringResource(R.string.tutorial_trophies_text_1)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.Category,
                                        description = stringResource(R.string.tutorial_trophies_text_2)
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.Edit,
                                        description = stringResource(R.string.tutorial_trophies_text_3)
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
