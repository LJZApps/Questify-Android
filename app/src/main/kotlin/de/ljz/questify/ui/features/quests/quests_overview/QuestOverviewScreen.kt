package de.ljz.questify.ui.features.quests.quests_overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.ljz.questify.R
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.ui.ds.components.GameBottomNavigation
import de.ljz.questify.ui.ds.components.GameBottomNavigationFab
import de.ljz.questify.ui.ds.components.GameBottomNavigationItem
import de.ljz.questify.ui.ds.components.GameDrawerAppBar
import de.ljz.questify.ui.ds.components.GameTextField
import de.ljz.questify.ui.ds.theme.bannerShape
import de.ljz.questify.ui.features.quests.create_quest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestDoneDialog
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestSortingBottomSheet
import de.ljz.questify.ui.features.quests.quests_overview.navigation.QuestBottomRoutes
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.AllQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.DailiesQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.HabitsQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.RoutinesQuestsPage
import de.ljz.questify.ui.navigation.BottomNavigationRoute
import de.ljz.questify.util.getSerializedRouteName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestOverviewScreen(
    drawerState: DrawerState,
    viewModel: QuestOverviewViewModel = hiltViewModel(),
    mainNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val questDoneDialogState = uiState.questDoneDialogState
    val allQuestPageState = uiState.allQuestPageState

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var showDropdown by remember { mutableStateOf(false) }

    val commands = listOf("add reminder", "set difficulty", "set due date", "set description")
    val filteredCommands = remember(uiState.fastAddingText) {
        if (uiState.fastAddingText.startsWith("/")) {
            commands.filter {
                it.contains(
                    uiState.fastAddingText.removePrefix("/"),
                    ignoreCase = true
                )
            }
        } else emptyList()
    }

    val fabShape by animateFloatAsState(
        targetValue = if (uiState.fastAddingText.isNotEmpty()) 100f else 50f, // 50f → komplett rund, 0f → normales FAB
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "FABShape"
    )

    val navigationBarHeight by animateDpAsState(
        targetValue = if (isKeyboardVisible()) 0.dp else 80.dp + WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding(), // 50f → komplett rund, 0f → normales FAB
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "FABShape"
    )

    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bottomNavRoutes = listOf(
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_all_quests),
            QuestBottomRoutes.AllQuests,
            Icons.AutoMirrored.Outlined.List
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_dailies),
            QuestBottomRoutes.Dailies,
            Icons.Outlined.CalendarMonth
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_routines),
            QuestBottomRoutes.Routines,
            Icons.Outlined.Schedule
        ),
        BottomNavigationRoute(
            stringResource(R.string.quest_screen_bottom_nav_rituals),
            QuestBottomRoutes.Habits,
            Icons.Outlined.Eco
        )
    )

    // Convert to GameBottomNavigationItem for our game-themed navigation
    val gameNavItems = bottomNavRoutes.map { route ->
        GameBottomNavigationItem(
            title = route.name,
            icon = route.icon,
            contentDescription = route.name
        )
    }
    val enabledViewQuestFeatures = listOf(
        QuestBottomRoutes.AllQuests
    )

    Scaffold(
        topBar = {
            GameDrawerAppBar(
                title = stringResource(R.string.quest_screen_top_bar_title),
                drawerState = drawerState,
                scrollBehavior = scrollBehavior,
                shape = MaterialTheme.shapes.bannerShape()
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                NavHost(
                    navController = bottomNavController,
                    startDestination = QuestBottomRoutes.AllQuests,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable<QuestBottomRoutes.AllQuests> {
                        AllQuestsPage(
                            state = uiState.allQuestPageState,
                            onQuestDone = {
                                viewModel.setQuestDone(
                                    it,
                                    context
                                )
                            },
                            onQuestDelete = viewModel::deleteQuest,
                            onSortButtonClick = viewModel::showSortingBottomSheet,
                            navController = mainNavController
                        )
                    }

                    composable<QuestBottomRoutes.Dailies> {
                        DailiesQuestsPage()
                    }

                    composable<QuestBottomRoutes.Routines> {
                        RoutinesQuestsPage()
                    }

                    composable<QuestBottomRoutes.Habits> {
                        HabitsQuestsPage()
                    }
                }
            }

            if (questDoneDialogState.visible) {
                QuestDoneDialog(
                    state = questDoneDialogState,
                    onDismiss = {
                        viewModel.hideQuestDoneDialog()
                    }
                )
            }

            if (uiState.isSortingBottomSheetOpen) {
                QuestSortingBottomSheet(
                    onDismiss = viewModel::hideSortingBottomSheet,
                    questSorting = allQuestPageState.sortingBy,
                    sortingDirection = allQuestPageState.sortingDirections,
                    showCompletedQuests = allQuestPageState.showCompleted,
                    onSortingChanged = viewModel::updateQuestSorting,
                    onSortingDirectionChanged = viewModel::updateQuestSortingDirection,
                    onShowCompletedQuestsChanged = { showCompletedQuests ->
                        viewModel.updateShowCompletedQuests(showCompletedQuests)
                        if (!showCompletedQuests && allQuestPageState.sortingBy == QuestSorting.DONE) {
                            viewModel.updateQuestSorting(QuestSorting.ID)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            // Fast adding text field
            if (uiState.featureSettings.fastQuestAddingEnabled && 
                !(scrollBehavior.state.collapsedFraction > 0.5f) && 
                enabledViewQuestFeatures.any { getSerializedRouteName(it) == currentDestination?.route }) {

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutSlowInEasing
                        )
                    ) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutSlowInEasing
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = LinearOutSlowInEasing
                        )
                    ) + slideOutVertically(
                        targetOffsetY = { it / 2 },
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = LinearOutSlowInEasing
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 16.dp)
                            .imePadding()
                    ) {
                        // Game-themed text field for fast adding
                        GameTextField(
                            value = uiState.fastAddingText,
                            onValueChange = {
                                viewModel.updateFastAddingText(it)
                            },
                            placeholder = stringResource(R.string.quest_overview_screen_fast_add_quest),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    viewModel.updateIsFastAddingFocused(it.isFocused)
                                },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (uiState.fastAddingText.isNotEmpty()) {
                                        viewModel.createFastQuest(uiState.fastAddingText)
                                    }
                                }
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Determine the selected item index based on the current destination
            val selectedItemIndex = bottomNavRoutes.indexOfFirst { route ->
                currentDestination?.route == getSerializedRouteName(route.route)
            }.takeIf { it >= 0 } ?: 0

            GameBottomNavigation(
                items = gameNavItems,
                selectedItemIndex = selectedItemIndex,
                onItemSelected = { index ->
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    val route = bottomNavRoutes[index].route
                    bottomNavController.navigate(route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(navigationBarHeight)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .imePadding(),
                floatingActionButton = if (enabledViewQuestFeatures.any {
                    getSerializedRouteName(it) == currentDestination?.route
                } && !(scrollBehavior.state.collapsedFraction > 0.5f)) {
                    {
                        GameBottomNavigationFab(
                            icon = if (uiState.fastAddingText.isNotEmpty()) Icons.Filled.Done else Icons.Filled.Add,
                            contentDescription = "Add Quest",
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (uiState.fastAddingText.isNotEmpty()) {
                                    viewModel.createFastQuest(uiState.fastAddingText)
                                } else {
                                    when (currentDestination?.route) {
                                        getSerializedRouteName(QuestBottomRoutes.AllQuests) -> mainNavController.navigate(
                                            CreateQuest()
                                        )
                                        // Other routes handling remains the same
                                    }
                                }
                            }
                        )
                    }
                } else null
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )
}

@Composable
fun isKeyboardVisible(): Boolean {
    val imeInsets = WindowInsets.ime.getBottom(LocalDensity.current)
    return imeInsets > 0
}
