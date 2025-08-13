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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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

    val context = LocalContext.current
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
                                currentStep++
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
            /*
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back/Cancel button
                    OutlinedButton(
                        onClick = {
                            if (currentStep > 0) {
                                currentStep--
                            } else {
                                mainNavController.navigateUp()
                            }
                        },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (currentStep > 0) Icons.AutoMirrored.Filled.ArrowBack else Icons.Filled.Close,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (currentStep > 0) stringResource(R.string.back) else stringResource(R.string.cancel),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Next/Save button
                    if (currentStep < steps.size - 1) {
                        Button(
                            onClick = { currentStep++ },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.next),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(18.dp)
                                    .rotate(180f)
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                viewModel.createQuest(
                                    onSuccess = {
                                        mainNavController.navigateUp()
                                    }
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.save),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
             */
        }
    )
}
