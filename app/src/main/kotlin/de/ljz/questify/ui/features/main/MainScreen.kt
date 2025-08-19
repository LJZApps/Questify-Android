package de.ljz.questify.ui.features.main

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.core.presentation.theme.QuestifyTheme
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


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3AdaptiveApi::class)
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
                    startDestination = Quests,
                    enterTransition = { scaleIntoContainer() },
                    exitTransition = { scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS) },
                    popEnterTransition = { scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS) },
                    popExitTransition = { scaleOutOfContainer() }
                ) {
                    composable<DashboardRoute> {
                        DashboardScreen(mainNavController, drawerState)
                    }

                    composable<Quests> {
                        QuestOverviewScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController,
                            homeNavHostController = homeNavHostController
                        )
                    }

                    composable<TrophiesRoute> {
                        TrophiesOverviewScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController
                        )
                    }
                }
            }
        }
    }
}
