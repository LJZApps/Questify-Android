package de.ljz.questify.ui.features.home

import androidx.activity.compose.BackHandler
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
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.dashboard.DashboardScreen
import de.ljz.questify.ui.features.dashboard.navigation.DashboardRoute
import de.ljz.questify.ui.features.home.components.DrawerContent
import de.ljz.questify.ui.features.quests.viewquests.ViewQuestsScreen
import de.ljz.questify.ui.features.quests.viewquests.navigation.Quests
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
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
fun HomeScreen(
    mainNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
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
        mainNavController.navigate(SettingsPermissionRoute)
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
                }
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
                    }

                    composable<Quests> {
                        ViewQuestsScreen(
                            drawerState = drawerState,
                            mainNavController = mainNavController
                        )
                    }
                }
            }
        }
    }
}