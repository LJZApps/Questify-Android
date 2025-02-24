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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import de.ljz.questify.util.NavBarConfig
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

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = false
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val homeNavHostController = rememberNavController()
    val allPermissionsGranted: Boolean = (isNotificationPermissionGranted(context) && isOverlayPermissionGranted(context) && isAlarmPermissionGranted(context))

    if (!allPermissionsGranted) {
        mainNavController.navigate(SettingsPermissionRoute(canNavigateBack = false))
    }

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
                    composable<DashboardRoute> {
                        DashboardScreen(
                            mainNavController = mainNavController,
                            drawerState = drawerState
                        )

                        if (!uiState.tutorialsUiState.dashboardOnboardingDone && uiState.tutorialsUiState.tutorialsEnabled) {
                            TutorialBottomSheet(
                                onDismiss = { tutorialsEnabled ->
                                    viewModel.setDashboardOnboardingDone()
                                    viewModel.toggleTutorials(tutorialsEnabled)
                                },
                                title = "Das Dashboard",
                                tutorialSteps = listOf(
                                    TutorialStep(
                                        icon = Icons.Default.Dashboard,
                                        description = "Willkommen im Dashboard! Hier hast du alle deine Statistiken, Quests und Fortschritte auf einen Blick."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.PieChart,
                                        description = "Verfolge deinen Fortschritt: Sieh dir deine abgeschlossenen Quests, täglichen Aufgaben und Routinen an."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.Settings,
                                        description = "Passe dein Dashboard individuell an: Zeige die Inhalte, die dir am wichtigsten sind."
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
                                title = "Deine Quests",
                                onDismiss = { tutorialsEnabled ->
                                    viewModel.setQuestOnboardingDone()
                                    viewModel.toggleTutorials(tutorialsEnabled)
                                },
                                tutorialSteps = listOf(
                                    TutorialStep(
                                        icon = Icons.AutoMirrored.Filled.List,
                                        description = "Hier findest du deine Quests – die großen Abenteuer und Herausforderungen, die dich deinem Ziel näher bringen."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Filled.CalendarMonth,
                                        description = "Dailies: Erstelle tägliche Aufgaben, die dich motivieren und dir helfen, einen konstanten Fortschritt zu erzielen."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Filled.Schedule,
                                        description = "Routinen: Automatisiere deine regelmäßigen Aufgaben und entwickle starke Gewohnheiten, die langfristig wirken."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Filled.Eco,
                                        description = "Gewohnheiten: Baue gesunde Gewohnheiten auf und belohne dich für jeden kleinen Erfolg."
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
                                title = "Deine Trophäen",
                                tutorialSteps = listOf(
                                    TutorialStep(
                                        icon = Icons.Default.EmojiEvents,
                                        description = "Hier findest du eine Übersicht über deine Trophäen. Sie repräsentieren deine Erfolge und werden direkt mit Quests verknüpft."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.Category,
                                        description = "Nutze die Kategorien, um deine Trophäen zu organisieren. Erstelle Kategorien und ordne deine Erfolge zu."
                                    ),
                                    TutorialStep(
                                        icon = Icons.Default.Edit,
                                        description = "Verwalte deine Trophäen und Kategorien. Passe Namen, Beschreibungen oder Zuordnungen an, um alles perfekt zu organisieren."
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