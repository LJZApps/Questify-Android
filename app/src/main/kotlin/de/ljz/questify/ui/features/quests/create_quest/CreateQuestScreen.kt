package de.ljz.questify.ui.features.quests.create_quest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.NavigateBefore
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.modals.CreateReminderDialog
import de.ljz.questify.ui.features.quests.create_quest.components.DueDateInfoDialog
import de.ljz.questify.ui.features.quests.create_quest.components.SetDueDateDialog
import de.ljz.questify.ui.features.quests.create_quest.sub_pages.BaseInformationPage
import de.ljz.questify.ui.features.quests.create_quest.sub_pages.DetailedInformationPage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateQuestScreen(
    mainNavController: NavHostController,
    viewModel: CreateQuestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    LocalContext.current
    var currentStep by remember { mutableIntStateOf(0) }

    val steps = listOf(
        NavigationItem(stringResource(R.string.create_quest_screen_general), Icons.Filled.Category),
        NavigationItem(stringResource(R.string.create_quest_screen_details), Icons.Filled.Description),
        /*NavigationItem(stringResource(R.string.create_quest_screen_trophies), Icons.Filled.EmojiEvents)*/
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.create_quest_top_bar_title)
                    )
                },
                subtitle = {
                    if (!uiState.title.trim().isEmpty())
                        Text(
                            text = uiState.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { mainNavController.navigateUp() },
                        shapes = IconButtonDefaults.shapes()
                    ) {
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
                // Enhanced stepper UI
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Step indicators
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            steps.forEachIndexed { index, (title, icon) ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when {
                                                    index < currentStep -> MaterialTheme.colorScheme.primary
                                                    index == currentStep -> MaterialTheme.colorScheme.primaryContainer
                                                    else -> MaterialTheme.colorScheme.surface
                                                }
                                            )
                                            .border(
                                                width = if (index == currentStep) 2.dp else 1.dp,
                                                color = if (index == currentStep) 
                                                    MaterialTheme.colorScheme.primary 
                                                else if (index < currentStep) 
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) 
                                                else 
                                                    MaterialTheme.colorScheme.outline,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            modifier = Modifier.size(28.dp),
                                            tint = when {
                                                index < currentStep -> MaterialTheme.colorScheme.onPrimary
                                                index == currentStep -> MaterialTheme.colorScheme.onPrimaryContainer
                                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                                            }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = title,
                                        style = if (index == currentStep) 
                                            MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold) 
                                        else 
                                            MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        color = if (index <= currentStep)
                                            MaterialTheme.colorScheme.onSurface
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // Add connector lines between steps
                                if (index < steps.size - 1) {
                                    Box(
                                        modifier = Modifier
                                            .weight(0.2f)
                                            .height(2.dp)
                                            .background(
                                                color = if (index < currentStep)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                            )
                                    )
                                }
                            }
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
                        /*2 -> TrophiesPage(
                            uiState = uiState,
                            onShowCreateReminderDialog = { viewModel.showCreateReminderDialog() },
                            onRemoveReminder = { viewModel.removeReminder(it) }
                        )*/
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
                    addingDateTimeState = uiState.addingDateTimeState,
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
                    },
                    addingDateTimeState = uiState.addingDateTimeState,
                    onDateTimeStateChange = { addingReminderState ->
                        viewModel.updateReminderState(addingReminderState)
                    }
                )
            }
        },
        bottomBar = {
            ShortNavigationBar {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (currentStep > 0) {
                                currentStep--
                            } else {
                                mainNavController.navigateUp()
                            }
                        },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            if (currentStep > 0) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.NavigateBefore,
                                    contentDescription = null
                                )

                                Text(
                                    text = stringResource(R.string.back)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )

                                Text(
                                    text = stringResource(R.string.cancel)
                                )
                            }
                        }
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (currentStep < steps.size - 1) {
                                currentStep++
                            } else {
                                viewModel.createQuest(
                                    onSuccess = {
                                        mainNavController.navigateUp()
                                    }
                                )
                            }
                        },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            if (currentStep < steps.size - 1) {
                                Text(
                                    text = stringResource(R.string.next)
                                )

                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.NavigateNext,
                                    contentDescription = null
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.save)
                                )

                                Icon(
                                    imageVector = Icons.Outlined.Save,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
            /*
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.BottomCenter
            ) {
                HorizontalFloatingToolbar(
                    expanded = true,
                    floatingActionButton = {
                        FloatingToolbarDefaults.StandardFloatingActionButton(
                            onClick = {
                                if (currentStep < steps.size - 1) {
                                    currentStep++
                                } else {
                                    viewModel.createQuest(
                                        onSuccess = {
                                            mainNavController.navigateUp()
                                        }
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                            )
                        }
                    }
                ) {
                    IconButton(onClick = {
                        if (currentStep > 0) {
                            currentStep--
                        } else {
                            mainNavController.navigateUp()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            }
             */
        }
    )
}
