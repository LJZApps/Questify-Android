package de.ljz.questify.ui.features.quests.quests_overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
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
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.ui.components.TopBar
import de.ljz.questify.ui.features.profile.view_profile.navigation.ProfileRoute
import de.ljz.questify.ui.features.quests.create_quest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestDoneDialog
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestSortingBottomSheet
import de.ljz.questify.ui.features.quests.quests_overview.navigation.QuestBottomRoutes
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.AllQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.DailiesQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.HabitsQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.RoutinesQuestsPage
import de.ljz.questify.ui.navigation.BottomNavigationRoute
import de.ljz.questify.util.bounceClick
import de.ljz.questify.util.getSerializedRouteName
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestOverviewScreen(
    drawerState: DrawerState,
    viewModel: QuestOverviewViewModel = hiltViewModel(),
    mainNavController: NavHostController,
    homeNavHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val questDoneDialogState = uiState.questDoneDialogState
    val allQuestPageState = uiState.allQuestPageState

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    val fabShape by animateFloatAsState(
        targetValue = if (uiState.fastAddingText.isNotEmpty()) 100f else 50f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "FABShape"
    )

    val navigationBarHeight by animateDpAsState(
        targetValue = if (isKeyboardVisible()) 0.dp else 80.dp + WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding(),
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
    val enabledViewQuestFeatures = listOf(
        QuestBottomRoutes.AllQuests
    )

    Scaffold(
        topBar = {
            TopBar(
                drawerState = drawerState,
                navController = mainNavController,
                title = stringResource(R.string.quest_screen_top_bar_title),
                scrollBehavior = scrollBehavior,
                actions = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Profilbild mit Hintergrund
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                mainNavController.navigate(ProfileRoute)
                                /*mainNavController.navigate(FullscreenDialogDemoRoute)*/
                            },
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                if (uiState.userState.profilePictureUrl.isNotEmpty()) {
                                    AsyncImage(
                                        model = uiState.userState.profilePictureUrl,
                                        contentDescription = "Profilbild",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Profilbild",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(5.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        // Badge oben rechts über dem Profilbild
                        /*if (uiState.newVersionVisible) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NewReleases,
                                    contentDescription = "New Version icon",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(16.dp) // Kleinere Größe für das Icon
                                )
                            }
                        }*/
                    }
                }
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
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .imePadding()
            ) {
                AnimatedVisibility(
                    visible = !(scrollBehavior.state.collapsedFraction > 0.5f) && enabledViewQuestFeatures.any {
                        getSerializedRouteName(
                            it
                        ) == currentDestination?.route
                    },
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (uiState.featureSettings.fastQuestAddingEnabled) {
                            OutlinedTextField(
                                value = uiState.fastAddingText,
                                onValueChange = {
                                    viewModel.updateFastAddingText(it)
                                },
                                placeholder = { Text(stringResource(R.string.quest_overview_screen_fast_add_quest)) },
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .weight(1f)
                                    .border(0.dp, Color.Transparent)
                                    .onFocusChanged {
                                        viewModel.updateIsFastAddingFocused(it.isFocused)
                                    }
                                    .shadow(6.dp, shape = CircleShape),
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
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

                        FloatingActionButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                if (uiState.fastAddingText.isNotEmpty()) {
                                    viewModel.createFastQuest(uiState.fastAddingText)
                                } else {
                                    when (currentDestination?.route) {
                                        getSerializedRouteName(QuestBottomRoutes.AllQuests) -> mainNavController.navigate(
                                            CreateQuest()
                                        )

                                        getSerializedRouteName(QuestBottomRoutes.Dailies) -> {
                                            // TODO
                                        }

                                        getSerializedRouteName(QuestBottomRoutes.Routines) -> {
                                            // TODO
                                        }

                                        getSerializedRouteName(QuestBottomRoutes.Habits) -> {
                                            // TODO
                                        }
                                    }
                                }
                            },
                            shape = RoundedCornerShape(fabShape),
                            modifier = Modifier
                                .size(56.dp)
                                .bounceClick(enabled = uiState.fastAddingText.isNotEmpty())
                        ) {
                            Crossfade(
                                targetState = uiState.fastAddingText.isNotEmpty(),
                                label = "IconFade"
                            ) { isFocused ->
                                Icon(
                                    imageVector = if (isFocused) Icons.Filled.Done else Icons.Filled.Add,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(navigationBarHeight)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .imePadding(),
            ) {
                bottomNavRoutes.forEach { bottomNavRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = bottomNavRoute.icon,
                                contentDescription = bottomNavRoute.name
                            )
                        },
                        label = { Text(bottomNavRoute.name) },
                        selected = currentDestination?.route == getSerializedRouteName(
                            bottomNavRoute.route
                        ),
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            bottomNavController.navigate(bottomNavRoute.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
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