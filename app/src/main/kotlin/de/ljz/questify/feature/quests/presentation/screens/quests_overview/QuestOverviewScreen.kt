package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.presentation.dialogs.CreateCategoryDialog
import de.ljz.questify.feature.quests.presentation.dialogs.QuestDoneDialog
import de.ljz.questify.feature.quests.presentation.dialogs.UpdateCategoryDialog
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.all_quests_page.AllQuestsPage
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.quest_for_category_page.QuestsForCategoryPage
import de.ljz.questify.feature.quests.presentation.sheets.ManageCategoryBottomSheet
import de.ljz.questify.feature.quests.presentation.sheets.QuestSortingBottomSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3WindowSizeClassApi::class
)
@Composable
fun QuestOverviewScreen(
    viewModel: QuestOverviewViewModel = hiltViewModel(),
    onNavigateToQuestDetailScreen: (Int) -> Unit,
    onNavigateToCreateQuestScreen: (Int?) -> Unit,
    onNavigateToEditQuestScreen: (Int) -> Unit,
    onToggleDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategoryForUpdating by viewModel.selectedCategoryForUpdating.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    QuestOverviewScreen(
        uiState = uiState,
        categories = categories,
        selectedCategoryForUpdating = selectedCategoryForUpdating,
        effectFlow = effectFlow,
        onUiEvent = { event ->
            when (event) {
                is QuestOverviewUiEvent.ToggleDrawer -> {
                    onToggleDrawer()
                }

                is QuestOverviewUiEvent.OnNavigateToQuestDetailScreen -> {
                    onNavigateToQuestDetailScreen(event.entryId)
                }

                is QuestOverviewUiEvent.OnNavigateToCreateQuestScreen -> {
                    onNavigateToCreateQuestScreen(event.categoryId)
                }

                is QuestOverviewUiEvent.OnNavigateToEditQuestScreen -> {
                    onNavigateToEditQuestScreen(event.id)
                }

                is QuestOverviewUiEvent.PerformHapticFeedback -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                else -> {
                    viewModel.onUiEvent(event = event)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun QuestOverviewScreen(
    uiState: QuestOverviewUIState,
    categories: List<QuestCategoryEntity>,
    selectedCategoryForUpdating: QuestCategoryEntity? = null,
    effectFlow: Flow<QuestOverviewUiEffect>,
    onUiEvent: (QuestOverviewUiEvent) -> Unit
) {
    val questDoneDialogState = uiState.questDoneDialogState
    val allQuestPageState = uiState.allQuestPageState

    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    val staticAllTab = QuestCategoryEntity(id = -1, text = stringResource(R.string.quest_overview_screen_tab_default_text))

    val allTabs = remember(categories) {
        listOf(staticAllTab) + categories
    }

    var desiredPageIndex by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effectFlow.collect { effect ->
                when (effect) {
                    is QuestOverviewUiEffect.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    BasicPlainTooltip(
                        text = "Navigationsleiste öffnen",
                        position = TooltipAnchorPosition.Below
                    ) {
                        IconButton(
                            onClick = {
                                onUiEvent(QuestOverviewUiEvent.ToggleDrawer)
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_menu),
                                contentDescription = "Localized description"
                            )
                        }
                    }
                },
                actions = {
                    BasicPlainTooltip(
                        text = "Suche",
                        position = TooltipAnchorPosition.Below
                    ) {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_search),
                                contentDescription = null
                            )
                        }
                    }

                    BasicPlainTooltip(
                        text = "Weitere Optionen",
                        position = TooltipAnchorPosition.Below
                    ) {
                        IconButton(
                            onClick = { dropdownExpanded = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more_vert),
                                contentDescription = null
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.quest_overview_screen_dropdown_sort_title)) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_filter_alt_filled),
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                                onUiEvent(QuestOverviewUiEvent.ShowDialog(DialogState.SortingBottomSheet))
                            }
                        )

                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.quest_overview_screen_dropdown_manage_list_title)) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_label_filled),
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                                onUiEvent(QuestOverviewUiEvent.ShowDialog(DialogState.ManageCategoriesBottomSheet))
                            }
                        )
                    }
                },
                title = {
                    Text(
                        text = "Quests",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            BasicPlainTooltip(
                text = "Neue Quest erstellen",
                position = TooltipAnchorPosition.Above
            ) {
                FloatingActionButton(
                    onClick = {
                        onUiEvent(
                            QuestOverviewUiEvent.OnNavigateToCreateQuestScreen(
                                categoryId = if ((desiredPageIndex - 1) < 0) null else (desiredPageIndex - 1)
                            )
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        },
        content = { innerPadding ->
            key(allTabs.map { it.id }) {
                val scrollState = rememberScrollState()
                val initialPage = desiredPageIndex.coerceIn(0, (allTabs.size - 1).coerceAtLeast(0))

                val pagerState = rememberPagerState(
                    initialPage = initialPage,
                    pageCount = { allTabs.size }
                )

                LaunchedEffect(pagerState.currentPage) {
                    desiredPageIndex = pagerState.currentPage
                }

                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .horizontalScroll(scrollState)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allTabs.forEachIndexed { index, tab ->
                            FilterChip(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    scope.launch {
                                        scrollState.animateScrollTo(index)
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                label = {
                                    Text(
                                        text = tab.text
                                    )
                                }
                            )
                        }

                        AssistChip(
                            onClick = {
                                onUiEvent(QuestOverviewUiEvent.ShowDialog(DialogState.CreateCategory))
                            },
                            label = {
                                Text(
                                    text = stringResource(R.string.quest_overview_screen_new_list_tab_title)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = null,
                                )
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
                                onEditQuest = {
                                    onUiEvent(QuestOverviewUiEvent.OnNavigateToEditQuestScreen(it))
                                },
                                onQuestChecked = { quest ->
                                    onUiEvent(
                                        QuestOverviewUiEvent.OnQuestChecked(
                                            questEntity = quest
                                        )
                                    )
                                },
                                onQuestClicked = { id ->
                                    onUiEvent(
                                        QuestOverviewUiEvent.OnNavigateToQuestDetailScreen(entryId = id)
                                    )
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            val categoryIndex = pageIndex - 1
                            if (categoryIndex < categories.size) {
                                val category = categories[categoryIndex]

                                QuestsForCategoryPage(
                                    categoryId = category.id,
                                    onNavigateToQuestDetailScreen = {
                                        onUiEvent(QuestOverviewUiEvent.OnNavigateToQuestDetailScreen(it))
                                    },
                                    onQuestDone = {
                                        onUiEvent(QuestOverviewUiEvent.OnQuestChecked(it))
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.dialogState == DialogState.QuestDone) {
                QuestDoneDialog(
                    state = questDoneDialogState,
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    }
                )
            }

            if (uiState.dialogState == DialogState.SortingBottomSheet) {
                QuestSortingBottomSheet(
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    sortingDirection = allQuestPageState.sortingDirections,
                    showCompletedQuests = allQuestPageState.showCompleted,
                    onSortingDirectionChanged = { sortingDirection ->
                        onUiEvent(QuestOverviewUiEvent.UpdateQuestSortingDirection(sortingDirection))
                    },
                    onShowCompletedQuestsChanged = { showCompletedQuests ->
                        onUiEvent(QuestOverviewUiEvent.UpdateShowCompletedQuests(value = showCompletedQuests))
                    }
                )
            }

            if (uiState.dialogState == DialogState.ManageCategoriesBottomSheet) {
                ManageCategoryBottomSheet(
                    categories = categories,
                    onCategoryRenameRequest = {
                        onUiEvent(QuestOverviewUiEvent.ShowUpdateCategoryDialog(it))
                    },
                    onCategoryRemove = {
                        onUiEvent.invoke(QuestOverviewUiEvent.DeleteQuestCategory(it))
                    },
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    }
                )
            }

            if (uiState.dialogState == DialogState.CreateCategory) {
                CreateCategoryDialog(
                    onConfirm = { listText ->
                        onUiEvent(QuestOverviewUiEvent.AddQuestCategory(value = listText))
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    }
                )
            }

            if (uiState.dialogState == DialogState.UpdateCategory) {
                UpdateCategoryDialog(
                    questCategory = selectedCategoryForUpdating,
                    onConfirm = { listText ->
                        onUiEvent(QuestOverviewUiEvent.UpdateQuestCategory(value = listText))
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
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