package de.ljz.questify.ui.features.quests.quests_overview

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import de.ljz.questify.core.presentation.components.expressive_menu.ExpressiveMenuItem
import de.ljz.questify.core.presentation.components.expressive_settings.ExpressiveSettingsSection
import de.ljz.questify.ui.features.profile.view_profile.navigation.ProfileRoute
import de.ljz.questify.ui.features.quests.create_quest.navigation.CreateQuest
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestDoneDialog
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestSortingBottomSheet
import de.ljz.questify.ui.features.quests.quests_overview.navigation.QuestBottomRoutes
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.AllQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.DailiesQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.HabitsQuestsPage
import de.ljz.questify.ui.features.quests.quests_overview.sub_pages.RoutinesQuestsPage
import de.ljz.questify.ui.navigation.BottomNavigationRoute
import de.ljz.questify.util.getSerializedRouteName
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    val searchBarScrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = {
                    Toast.makeText(context, "Suche: $it", Toast.LENGTH_SHORT).show()
                },
                placeholder = {
                    Text(
                        text = "Quests suchen"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchBarState.currentValue == SearchBarValue.Collapsed) {
                        FilledTonalIconButton(
                            onClick = viewModel::showSortingBottomSheet,
                            shape = MaterialShapes.Pill.toShape()
                        ) {
                            Icon(Icons.Default.SwapVert, contentDescription = null)
                        }
                    } else {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    searchBarState.animateToCollapsed()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }

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

    Scaffold(
        topBar = {
            AppBarWithSearch(
                scrollBehavior = searchBarScrollBehavior,
                state = searchBarState,
                inputField = inputField,
                colors =
                    SearchBarDefaults.appBarWithSearchColors(
                        appBarContainerColor = Color.Transparent
                    ),
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Profilbild mit Hintergrund
                        FilledTonalIconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                mainNavController.navigate(ProfileRoute)
                            },
                            shape = MaterialShapes.Cookie9Sided.toShape()
                        ) {
                            if (uiState.userState.profilePictureUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = uiState.userState.profilePictureUrl,
                                    contentDescription = "Profilbild",
                                    modifier = Modifier
                                        .size(40.dp),
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
                }
            )
            ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
                ExpressiveSettingsSection(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    uiState.allQuestPageState.quests
                        .filter { it.title.contains(textFieldState.text, ignoreCase = true) || it.notes?.contains(textFieldState.text, ignoreCase = true) == true }
                        .forEach { questEntity ->
                            ExpressiveMenuItem(
                                title = questEntity.title,
                                onClick = {
                                    mainNavController.navigate(QuestDetail(id = questEntity.id))
                                }
                            )
                        }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButtonMenu(
                expanded = fabMenuExpanded,
                button = {
                    ToggleFloatingActionButton(
                        checked = fabMenuExpanded,
                        onCheckedChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            fabMenuExpanded = !fabMenuExpanded
                        }
                    ) {
                        val imageVector by remember {
                            derivedStateOf {
                                if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                            }
                        }
                        Icon(
                            painter = rememberVectorPainter(imageVector),
                            contentDescription = null,
                            modifier = Modifier.animateIcon({ checkedProgress }),
                        )
                    }
                }
            ) {
                FloatingActionButtonMenuItem(
                    onClick = {
                        fabMenuExpanded = false
                        mainNavController.navigate(CreateQuest())
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.List,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = stringResource(R.string.quest_overview_screen_fab_create_quest)) },
                )

                FloatingActionButtonMenuItem(
                    onClick = { fabMenuExpanded = false },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = stringResource(R.string.quest_overview_screen_fab_create_daily)) },
                )

                FloatingActionButtonMenuItem(
                    onClick = { fabMenuExpanded = false },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null
                        )
                    },
                    text = { Text(text = stringResource(R.string.quest_overview_screen_fab_create_routine)) },
                )

                FloatingActionButtonMenuItem(
                    onClick = { fabMenuExpanded = false },
                    icon = { Icon(imageVector = Icons.Outlined.Eco, contentDescription = null) },
                    text = { Text(text = stringResource(R.string.quest_overview_screen_fab_create_habit)) },
                )
            }
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
        bottomBar = {
            FlexibleBottomAppBar {
                bottomNavRoutes.forEach { bottomNavRoute ->
                    NavigationBarItem(
                        alwaysShowLabel = true,
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun QuestOverviewScreenPreview() {
    QuestOverviewScreen(
        drawerState = DrawerState(DrawerValue.Closed),
        mainNavController = rememberNavController(),
        homeNavHostController = rememberNavController()
    )
}
