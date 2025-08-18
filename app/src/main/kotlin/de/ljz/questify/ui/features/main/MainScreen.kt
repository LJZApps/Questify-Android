package de.ljz.questify.ui.features.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.material3.toShape
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import androidx.navigation.navDeepLink
import de.ljz.questify.core.presentation.theme.QuestifyTheme
import de.ljz.questify.ui.features.first_setup.navigation.FirstSetupRoute
import de.ljz.questify.ui.features.quests.quest_detail.QuestDetailScreen
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quests_overview.QuestOverviewScreen
import de.ljz.questify.ui.features.settings.permissions.navigation.SettingsPermissionRoute
import de.ljz.questify.util.isAlarmPermissionGranted
import de.ljz.questify.util.isNotificationPermissionGranted
import de.ljz.questify.util.isOverlayPermissionGranted
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3ExpressiveApi::class
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

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<ThreePaneScaffoldRole>(

    )

    val isDetailFlowActive = remember(mainNavController.currentBackStackEntryAsState().value) {
        mainNavController.currentDestination?.route != FirstSetupRoute.serializer().descriptor.serialName // And other conditions
    }
    val windowSizeClass = calculateWindowSizeClass(LocalActivity.current as Activity)

    LaunchedEffect(isDetailFlowActive, scaffoldNavigator.currentDestination, windowSizeClass.widthSizeClass) {
        val currentPaneRole = scaffoldNavigator.currentDestination?.pane
        val targetPaneRole = if (isDetailFlowActive) {
            ThreePaneScaffoldRole.Primary
        } else {
            ThreePaneScaffoldRole.Secondary
        }
        if (currentPaneRole != targetPaneRole) {
            scaffoldNavigator.navigateTo(targetPaneRole)
        }
    }

    BackHandler(enabled = scaffoldNavigator.canNavigateBack() || isDetailFlowActive) {
        if (isDetailFlowActive) {
            if (mainNavController.previousBackStackEntry == null) { // At the root of detail flow
                // Navigate mainNavController to EmptyDetailScreenDestination
                mainNavController.navigate(FirstSetupRoute.serializer().descriptor.serialName) {
                    popUpTo(mainNavController.graph.findStartDestination().id) { inclusive = true }
                    launchSingleTop = true
                }
            } else { // Deeper in detail flow
                mainNavController.popBackStack()
            }
        } else if (scaffoldNavigator.canNavigateBack()) { // For scaffold-level back
            scope.launch { scaffoldNavigator.navigateBack() }
        }
        // Else, system back handles it (e.g., closing app from list)
    }

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
                    onClick = { }
                )
            },
            layoutType = NavigationSuiteType.None
        ) {

            NavigableListDetailPaneScaffold(
                navigator = scaffoldNavigator,
                listPane = {
                    AnimatedPane {
                        QuestOverviewScreen(
                            mainNavController = mainNavController,
                            homeNavHostController = homeNavController,
                            navRailState = state,
                            scaffoldNavigator = scaffoldNavigator
                        )
                    }
                },
                detailPane = {
                    AnimatedPane {
                        scaffoldNavigator.currentDestination?.contentKey?.let {
                            QuestDetailScreen(navController = homeNavController)
                        }
                        NavHost(
                            navController = homeNavController,
                            startDestination = FirstSetupRoute
                        ) {
                            composable<QuestDetail>(
                                deepLinks = listOf(
                                    navDeepLink<QuestDetail>(basePath = "questify://quest_detail")
                                ),
                            ) { backStackEntry ->
                                QuestDetailScreen(navController = homeNavController)
                            }

                            composable<FirstSetupRoute> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = Icons.Default.FilterNone,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.secondaryContainer,
                                                shape = MaterialShapes.Cookie4Sided.toShape()
                                            )
                                            .padding(18.dp)
                                            .size(64.dp),
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Text("Wähle eine Quest zum anzeigen aus.")
                                    Spacer(modifier = Modifier.weight(1f))
                                }
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
