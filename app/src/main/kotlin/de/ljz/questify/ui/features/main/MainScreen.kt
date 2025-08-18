package de.ljz.questify.ui.features.main

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import de.ljz.questify.core.presentation.theme.QuestifyTheme
import de.ljz.questify.ui.features.first_setup.FirstSetupScreen
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.quests.quest_detail.QuestDetailScreen
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quests_overview.QuestOverviewScreen
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.util.isAlarmPermissionGranted
import de.ljz.questify.util.isNotificationPermissionGranted
import de.ljz.questify.util.isOverlayPermissionGranted


@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3WindowSizeClassApi::class,
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
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator<ListDetailPaneScaffoldRole>()

    QuestifyTheme {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                item(
                    icon = {
                        Icon(
                            Icons.Filled.List,
                            contentDescription = "Quests"
                        )
                    },
                    label = { Text("Quests") },
                    selected = true,
                    onClick = {  }
                )
            },
            layoutType = NavigationSuiteType.WideNavigationRailExpanded
        ) {
            NavigableListDetailPaneScaffold(
                navigator = scaffoldNavigator,
                listPane = {
                    AnimatedPane {
                        QuestOverviewScreen(
                            mainNavController = mainNavController,
                            homeNavHostController = homeNavController,
                            navRailState = state
                        )
                    }
                },
                detailPane = {
                    AnimatedPane {
                        NavHost(
                            navController = homeNavController,
                            startDestination = FirstSetupRoute
                        ) {
                            composable<QuestDetail>(
                                deepLinks = listOf(
                                    navDeepLink<QuestDetail>(basePath = "questify://quest_detail")
                                ),
                            ) { backStackEntry ->
                                QuestDetailScreen(navController = mainNavController)
                            }

                            composable<FirstSetupRoute> {
                                FirstSetupScreen(navController = mainNavController)
                            }
                        }
                    }
                },
            )
        }
        /*
        SentryTraced(tag = "home_screen") {
            Row {
                ModalWideNavigationRail(
                    state = state,
                    hideOnCollapse = !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND),
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
                    val selected = currentDestination?.route == getSerializedRouteName(Quests)
                    WideNavigationRailItem(
                        selected = selected,
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
                        label = {
                            Text(
                                text = "Quests",
                                color = if (state.targetValue == WideNavigationRailValue.Expanded && selected)
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        railExpanded = state.targetValue == WideNavigationRailValue.Expanded,
                    )

                    WideNavigationRailItem(
                        selected = false,
                        onClick = {
                            mainNavController.navigate(
                                SettingsMainRoute
                            ) {
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Filled.Settings, null) },
                        label = {
                            Text(
                                text = "Einstellungen"
                            )
                        },
                        railExpanded = state.targetValue == WideNavigationRailValue.Expanded,
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
                            navRailState = state
                        )
                    }
                }
            }
        }
         */
    }
}
