package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuItem
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSection
import de.ljz.questify.core.presentation.components.filled_tonal_icon_button.NarrowIconButton
import de.ljz.questify.core.utils.QuestSorting
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.presentation.dialogs.CreateCategoryDialog
import de.ljz.questify.feature.quests.presentation.dialogs.QuestDoneDialog
import de.ljz.questify.feature.quests.presentation.dialogs.UpdateCategoryDialog
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestRoute
import de.ljz.questify.feature.quests.presentation.screens.quest_detail.QuestDetailRoute
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.all_quests_page.AllQuestsPage
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.quest_for_category_page.QuestsForCategoryPage
import de.ljz.questify.feature.quests.presentation.sheets.ManageCategoryBottomSheet
import de.ljz.questify.feature.quests.presentation.sheets.QuestSortingBottomSheet
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3WindowSizeClassApi::class
)
@Composable
fun QuestOverviewScreen(
    drawerState: DrawerState,
    viewModel: QuestOverviewViewModel = hiltViewModel(),
    mainNavController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val questDoneDialogState = uiState.questDoneDialogState
    val allQuestPageState = uiState.allQuestPageState
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategoryForUpdating by viewModel.selectedCategoryForUpdating.collectAsStateWithLifecycle()

    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    val staticAllTab = QuestCategoryEntity(id = -1, text = stringResource(R.string.quest_overview_screen_tab_default_text))

    val allTabs = remember(categories) {
        listOf(staticAllTab) + categories
    }

    var desiredPageIndex by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var dropdownExpanded by remember { mutableStateOf(false) }

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = {
                    // TODO
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_quests_title)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = searchBarState.progress >= 0.5f,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        FilledTonalIconButton(
                            onClick = {
                                scope.launch {
                                    searchBarState.animateToCollapsed()
                                    textFieldState.clearText()
                                }
                            },
                            shapes = IconButtonDefaults.shapes()
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

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBarWithSearch(
                state = searchBarState,
                inputField = inputField,
                colors =
                    SearchBarDefaults.appBarWithSearchColors(
                        appBarContainerColor = Color.Transparent
                    ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (drawerState.currentValue == DrawerValue.Closed) open() else close()
                                }
                            }
                        },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    NarrowIconButton(
                        onClick = { dropdownExpanded = true }
                    ) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.quest_overview_screen_dropdown_sort_title)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.FilterAlt,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                                viewModel.showSortingBottomSheet()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.quest_overview_screen_dropdown_manage_list_title)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.Label,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                                viewModel.showManageCategoriesBottomSheet()
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
            ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
                ExpressiveSettingsSection(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    uiState.allQuestPageState.quests
                        .filter {
                            it.title.contains(
                                textFieldState.text,
                                ignoreCase = true
                            ) || it.notes?.contains(textFieldState.text, ignoreCase = true) == true
                        }
                        .forEach { questEntity ->
                            ExpressiveMenuItem(
                                title = questEntity.title,
                                onClick = {
                                    mainNavController.navigate(QuestDetailRoute(id = questEntity.id))
                                }
                            )
                        }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    mainNavController.navigate(
                        route = CreateQuestRoute(
                            selectedCategoryIndex = if ((desiredPageIndex - 1) < 0) null else (desiredPageIndex - 1)
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        },
        content = { innerPadding ->
            key(allTabs.size) {
                val initialPage = desiredPageIndex.coerceIn(0, (allTabs.size - 1).coerceAtLeast(0))

                val pagerState = rememberPagerState(
                    initialPage = initialPage,
                    pageCount = { allTabs.size }
                )

                LaunchedEffect(pagerState.currentPage) {
                    desiredPageIndex = pagerState.currentPage
                }

                val tabIndex = pagerState.currentPage

                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    PrimaryScrollableTabRow(
                        selectedTabIndex = tabIndex,
                        edgePadding = 0.dp,
                        minTabWidth = 0.dp
                    ) {
                        allTabs.forEachIndexed { index, tab ->
                            val isSelected = pagerState.currentPage == index
                            Tab(
                                selected = isSelected,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                text = {
                                    Text(
                                        text = tab.text,
                                        color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                        }

                        Tab(
                            selected = false,
                            onClick = {
                                viewModel.showCreateCategoryDialog()
                            },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = stringResource(R.string.quest_overview_screen_new_list_tab_title),
                                        modifier = Modifier.padding(start = 8.dp),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        )
                    }

                    HorizontalPager(
                        state = pagerState,
                        key = { pageIndex ->
                            allTabs.getOrNull(pageIndex)?.id ?: "temp_page_$pageIndex"
                        }
                    ) { pageIndex ->
                        if (pageIndex == 0) {
                            AllQuestsPage(
                                state = uiState.allQuestPageState,
                                onQuestDone = {
                                    viewModel.setQuestDone(
                                        it,
                                        context
                                    )
                                },
                                onQuestDelete = viewModel::deleteQuest,
                                navController = mainNavController,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            val categoryIndex = pageIndex - 1
                            if (categoryIndex < categories.size) {
                                val category = categories[categoryIndex]

                                QuestsForCategoryPage(
                                    categoryId = category.id,
                                    navController = mainNavController,
                                    onQuestDone = {
                                        viewModel.setQuestDone(
                                            it,
                                            context
                                        )
                                    },
                                    onQuestDelete = viewModel::deleteQuest,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
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

            if (uiState.isManageCategoriesBottomSheetOpen) {
                ManageCategoryBottomSheet(
                    categories = categories,
                    onCategoryRenameRequest = {
                        viewModel.showUpdateCategoryDialog(it)
                    },
                    onCategoryRemove = {
                        viewModel.deleteQuestCategory(it)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Liste gelöscht",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    onDismiss = {
                        viewModel.hideManageCategoriesBottomSheet()
                    }
                )
            }

            if (uiState.isCreateCategoryDialogOpen) {
                CreateCategoryDialog(
                    onConfirm = { listText ->
                        viewModel.addQuestCategory(listText)
                        viewModel.hideCreateCategoryDialog()
                    },
                    onDismiss = {
                        viewModel.hideCreateCategoryDialog()
                    }
                )
            }

            if (uiState.isUpdateCategoryDialogOpen) {
                UpdateCategoryDialog(
                    questCategory = selectedCategoryForUpdating,
                    onConfirm = { listText ->
                        viewModel.updateQuestCategory(listText)
                        viewModel.hideUpdateCategoryDialog()
                    },
                    onDismiss = {
                        viewModel.hideUpdateCategoryDialog()
                    }
                )

            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    )
}