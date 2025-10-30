package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.chips.ListChip
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.presentation.dialogs.CreateCategoryDialog
import de.ljz.questify.feature.quests.presentation.dialogs.DeleteQuestCategoryDialog
import de.ljz.questify.feature.quests.presentation.dialogs.QuestDoneDialog
import de.ljz.questify.feature.quests.presentation.dialogs.RenameCategoryDialog
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.all_quests_page.AllQuestsPage
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.quest_for_category_page.QuestsForCategoryPage
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
    val effectFlow = viewModel.effect

    QuestOverviewScreen(
        uiState = uiState,
        categories = categories,
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
    effectFlow: Flow<QuestOverviewUiEffect>,
    onUiEvent: (QuestOverviewUiEvent) -> Unit
) {
    val allQuestPageState = uiState.allQuestPageState

    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val staticAllTab = QuestCategoryEntity(
        id = -1,
        text = stringResource(R.string.quest_overview_screen_tab_default_text)
    )

    val allTabs = remember(categories) {
        listOf(staticAllTab) + categories
    }

    var desiredPageIndex by rememberSaveable { mutableIntStateOf(0) }

    val scrollState = rememberScrollState()
    val initialPage = desiredPageIndex.coerceIn(0, (allTabs.size - 1).coerceAtLeast(0))

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { allTabs.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        desiredPageIndex = pagerState.currentPage
    }

    val requesters = remember(allTabs.size) {
        List(allTabs.size) {
            BringIntoViewRequester()
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        scope.launch {
            requesters[pagerState.currentPage].bringIntoView()
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effectFlow.collect { effect ->
                when (effect) {
                    is QuestOverviewUiEffect.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            withDismissAction = effect.withDismissAction
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
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
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        onUiEvent(
                            QuestOverviewUiEvent.OnNavigateToCreateQuestScreen(
                                categoryId = if ((desiredPageIndex - 1) < 0) null else (desiredPageIndex - 1)
                            )
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    allTabs.forEachIndexed { index, tab ->
                        var chipDropdownExpanded by remember { mutableStateOf(false) }
                        val interactionSource = remember { MutableInteractionSource() }

                        Box(
                            modifier = Modifier
                                .bringIntoViewRequester(requesters[index]),
                        ) {
                            ListChip(
                                selected = pagerState.currentPage == index,
                                interactionSource = interactionSource,
                                onClick = {
                                    scope.launch {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                onLongClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                    chipDropdownExpanded = true
                                },
                                label = {
                                    Text(
                                        text = tab.text
                                    )
                                }
                            )

                            DropdownMenu(
                                expanded = chipDropdownExpanded,
                                onDismissRequest = { chipDropdownExpanded = false },
                                offset = DpOffset(x = 0.dp, y = 8.dp)
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.manage_categories_bottom_sheet_dropdown_rename_title)) },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_edit_filled),
                                            contentDescription = null
                                        )
                                    },
                                    onClick = {
                                        chipDropdownExpanded = false
                                        onUiEvent(
                                            QuestOverviewUiEvent.ShowUpdateCategoryDialog(
                                                questCategoryEntity = tab
                                            )
                                        )
                                    }
                                )

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(R.string.manage_categories_bottom_sheet_dropdown_delete_title),
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_delete_filled),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    },
                                    onClick = {
                                        chipDropdownExpanded = false

                                        onUiEvent(
                                            QuestOverviewUiEvent.ShowDialog(
                                                DialogState.DeleteCategory(tab)
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    ListChip(
                        selected = false,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onUiEvent(QuestOverviewUiEvent.ShowDialog(DialogState.CreateCategory))
                        },
                        label = {},
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
                            onCreateNewQuestButtonClicked = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                onUiEvent(
                                    QuestOverviewUiEvent.OnNavigateToCreateQuestScreen(
                                        categoryId = if ((desiredPageIndex - 1) < 0) null else (desiredPageIndex - 1)
                                    )
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

            if (uiState.dialogState is DialogState.QuestDone) {
                val questDoneDialogState = uiState.dialogState.questDoneDialogState
                QuestDoneDialog(
                    state = questDoneDialogState,
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    }
                )
            }

            if (uiState.dialogState is DialogState.SortingBottomSheet) {
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

            if (uiState.dialogState is DialogState.CreateCategory) {
                CreateCategoryDialog(
                    onConfirm = { value ->
                        onUiEvent(QuestOverviewUiEvent.AddQuestCategory(value = value))
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    initialInputFocussed = true
                )
            }

            if (uiState.dialogState is DialogState.UpdateCategory) {
                RenameCategoryDialog(
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    onConfirm = { value ->
                        onUiEvent(
                            QuestOverviewUiEvent.UpdateQuestCategory(
                                questCategoryEntity = uiState.dialogState.questCategoryEntity,
                                value = value
                            )
                        )
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    initialInputFocussed = true,
                    initialValue = uiState.dialogState.questCategoryEntity.text
                )
            }

            if (uiState.dialogState is DialogState.DeleteCategory) {
                DeleteQuestCategoryDialog(
                    onConfirm = {
                        onUiEvent(
                            QuestOverviewUiEvent.DeleteQuestCategory(
                                questCategoryEntity = uiState.dialogState.questCategoryEntity
                            )
                        )
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    onDismiss = {
                        onUiEvent(QuestOverviewUiEvent.CloseDialog)
                    },
                    questCategoryEntity = uiState.dialogState.questCategoryEntity
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        }
    )
}