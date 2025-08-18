package de.ljz.questify.ui.features.main

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalWideNavigationRail
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.core.presentation.theme.QuestifyTheme
import de.ljz.questify.ui.features.quests.quests_overview.QuestOverviewScreen
import de.ljz.questify.ui.features.quests.quests_overview.navigation.Quests
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.ui.navigation.ScaleTransitionDirection
import de.ljz.questify.ui.navigation.scaleIntoContainer
import de.ljz.questify.ui.navigation.scaleOutOfContainer
import de.ljz.questify.util.getSerializedRouteName
import de.ljz.questify.util.isAlarmPermissionGranted
import de.ljz.questify.util.isNotificationPermissionGranted
import de.ljz.questify.util.isOverlayPermissionGranted
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)
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

    val homeNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val state = rememberWideNavigationRailState()
    val scope = rememberCoroutineScope()

    // 📐 Adaptive Info
    val windowInfo = calculateWindowSizeClass(LocalActivity.current as Activity)
    val isTablet = windowInfo.widthSizeClass != WindowWidthSizeClass.Compact

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    QuestifyTheme {
        SentryTraced(tag = "home_screen") {
            Row {
                ModalWideNavigationRail(
                    state = state,
                    header = {
                        IconButton(
                            modifier =
                                Modifier.padding(start = 24.dp),
                            onClick = {
                                scope.launch {
                                    if (state.targetValue == WideNavigationRailValue.Expanded)
                                        state.collapse()
                                    else state.expand()
                                }
                            },
                        ) {
                            if (state.targetValue == WideNavigationRailValue.Expanded) {
                                Icon(Icons.AutoMirrored.Filled.MenuOpen, "Collapse rail")
                            } else {
                                Icon(Icons.Filled.Menu, "Expand rail")
                            }
                        }
                    },
                ) {
                    WideNavigationRailItem(
                        selected = currentDestination?.route == getSerializedRouteName(Quests),
                        onClick = {
                            if (currentDestination?.route != getSerializedRouteName(Quests)) homeNavController.navigate(
                                Quests
                            ) {
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                        label = { Text("Quests") },
                        railExpanded = state.targetValue == WideNavigationRailValue.Expanded
                    )
                }

                NavHost(
                    navController = homeNavController,
                    startDestination = Quests,
                    modifier = Modifier.weight(1f),
                    enterTransition = { scaleIntoContainer() },
                    exitTransition = { scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS) },
                    popEnterTransition = { scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS) },
                    popExitTransition = { scaleOutOfContainer() }
                ) {
                    composable<Quests> {
                        QuestOverviewScreen(
                            mainNavController = mainNavController,
                            homeNavHostController = homeNavController,
                            drawerState = drawerState
                        )
                    }
                }
            }
        }
    }
}
