package de.ljz.questify.ui.features.quests.create_quest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.components.CreateReminderDialog
import de.ljz.questify.ui.features.quests.create_quest.components.DueDateInfoDialog
import de.ljz.questify.ui.features.quests.create_quest.components.SetDueDateDialog
import de.ljz.questify.ui.features.quests.create_quest.sub_pages.BaseInformationPage
import de.ljz.questify.ui.features.quests.create_quest.sub_pages.DetailedInformationPage
import de.ljz.questify.ui.features.quests.create_quest.sub_pages.TrophiesPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestScreen(
    mainNavController: NavHostController,
    viewModel: CreateQuestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(0) }

    val steps = listOf(
        NavigationItem(stringResource(R.string.create_quest_screen_general), Icons.Filled.Category),
        NavigationItem(stringResource(R.string.create_quest_screen_details), Icons.Filled.Description),
        NavigationItem(stringResource(R.string.create_quest_screen_trophies), Icons.Filled.EmojiEvents)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(uiState.title.trim().isEmpty()) stringResource(R.string.create_quest_top_bar_title) else uiState.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    steps.forEachIndexed { index, (title, icon) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            index < currentStep -> MaterialTheme.colorScheme.primary
                                            index == currentStep -> MaterialTheme.colorScheme.primaryContainer
                                            else -> MaterialTheme.colorScheme.surface
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (index == currentStep || index < currentStep) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurfaceVariant,
                                        shape = CircleShape
                                    )
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = when {
                                        index < currentStep -> MaterialTheme.colorScheme.onPrimary
                                        index == currentStep -> MaterialTheme.colorScheme.onPrimaryContainer
                                        else -> MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = if (index <= currentStep)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                }

                // Step content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    when (currentStep) {
                        0 -> BaseInformationPage(
                            uiState = uiState,
                            onTitleChange = { viewModel.updateTitle(it) },
                            onDifficultyChange = { viewModel.updateDifficulty(it) }
                        )
                        1 -> DetailedInformationPage(
                            uiState = uiState,
                            onNotesChange = { viewModel.updateDescription(it) },
                            onShowDueDateInfoDialog = { viewModel.showDueDateInfoDialog() },
                            onShowAddingDueDateDialog = { viewModel.showAddingDueDateDialog() },
                            onRemoveDueDate = { viewModel.removeDueDate() },
                            onShowCreateReminderDialog = { viewModel.showCreateReminderDialog() },
                            onRemoveReminder = { viewModel.removeReminder(it) }
                        )
                        2 -> TrophiesPage(
                            uiState = uiState,
                            onShowCreateReminderDialog = { viewModel.showCreateReminderDialog() },
                            onRemoveReminder = { viewModel.removeReminder(it) }
                        )
                    }
                }
            }

            if (uiState.isAddingReminder) {
                CreateReminderDialog(
                    onDismiss = viewModel::hideCreateReminderDialog,
                    onConfirm = { timestamp ->
                        viewModel.addReminder(timestamp)
                        viewModel.hideCreateReminderDialog()
                    },
                    addingReminderState = uiState.addingReminderState,
                    onReminderStateChange = { addingReminderState ->
                        viewModel.updateReminderState(addingReminderState)
                    }
                )
            }

            if (uiState.isDueDateInfoDialogVisible) {
                DueDateInfoDialog(
                    onDismiss = {
                        viewModel.hideDueDateInfoDialog()
                    }
                )
            }

            if (uiState.isAddingDueDate) {
                SetDueDateDialog(
                    onConfirm = { dueDateTimestamp ->
                        viewModel.setDueDate(dueDateTimestamp)
                        viewModel.hideAddingDueDateDialog()
                    },
                    onDismiss = {
                        viewModel.hideAddingDueDateDialog()
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
                    .imePadding(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { if (currentStep > 0) currentStep-- },
                        enabled = currentStep > 0
                    ) {
                        Text(stringResource(R.string.back))
                    }
                    if (currentStep < steps.size - 1) {
                        Button(
                            onClick = { currentStep++ }
                        ) {
                            Text(stringResource(R.string.next))
                        }
                    } else {
                        Button(
                            onClick = {
                                viewModel.createQuest(
                                    onSuccess = {
                                        mainNavController.navigateUp()
                                    }
                                )
                            }
                        ) {
                            Text(stringResource(R.string.save))
                        }
                    }
                }
            }
        }
    )
}