package de.ljz.questify.feature.main.presentation.screens.main

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.core.presentation.navigation.ScaleTransitionDirection
import de.ljz.questify.core.presentation.navigation.scaleIntoContainer
import de.ljz.questify.core.presentation.navigation.scaleOutOfContainer
import de.ljz.questify.core.presentation.theme.QuestifyTheme
import de.ljz.questify.core.utils.isAlarmPermissionGranted
import de.ljz.questify.core.utils.isNotificationPermissionGranted
import de.ljz.questify.core.utils.isOverlayPermissionGranted
import de.ljz.questify.feature.habits.presentation.screens.habits.HabitsRoute
import de.ljz.questify.feature.habits.presentation.screens.habits.HabitsScreen
import de.ljz.questify.feature.main.presentation.components.DrawerContent
import de.ljz.questify.feature.player_stats.presentation.screens.stats.StatsRoute
import de.ljz.questify.feature.player_stats.presentation.screens.stats.StatsScreen
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.quest_detail.QuestDetailRoute
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestOverviewScreen
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestsRoute
import de.ljz.questify.feature.settings.presentation.screens.permissions.SettingsPermissionRoute
import io.sentry.compose.SentryTraced

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainScreen(
    mainNavController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
                }
            ) {
                NavHost(
                    navController = homeNavHostController,
                    startDestination = QuestsRoute,
                    enterTransition = { scaleIntoContainer() },
                    exitTransition = { scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS) },
                    popEnterTransition = { scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS) },
                    popExitTransition = { scaleOutOfContainer() }
                ) {
                    composable<QuestsRoute> {
                        QuestOverviewScreen(
                            drawerState = drawerState,
                            onNavigateToQuestDetailScreen = {
                                mainNavController.navigate(QuestDetailRoute(id = it))
                            },
                            onNavigateToCreateQuestScreen = {
                                mainNavController.navigate(
                                    CreateQuestRoute(
                                        selectedCategoryIndex = it
                                    )
                                )
                            }
                        )
                    }

                    composable<HabitsRoute> {
                        HabitsScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController
                        )
                    }

                    composable<StatsRoute> {
                        StatsScreen(
                            drawerState = drawerState,
                            navController = mainNavController
                        )
                    }
                }
            }
        }
    }
}
