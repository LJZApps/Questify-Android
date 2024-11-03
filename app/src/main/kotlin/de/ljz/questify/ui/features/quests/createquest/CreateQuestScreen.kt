package de.ljz.questify.ui.features.quests.createquest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.components.CreateReminderDialog
import de.ljz.questify.ui.features.quests.createquest.components.DueDateInfoDialog
import de.ljz.questify.ui.features.quests.createquest.components.SetDueDateDialog
import de.ljz.questify.util.NavBarConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestScreen(
    mainNavController: NavHostController,
    viewModel: CreateQuestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val options = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )
    val context = LocalContext.current
    val reminderDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm 'Uhr'", Locale.getDefault())
    val dueDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    LaunchedEffect(Unit) {

        NavBarConfig.transparentNavBar = true
    }

    LaunchedEffect(Unit) {

        NavBarConfig.transparentNavBar = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.create_quest_top_bar_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = { mainNavController.navigateUp() }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.createQuest(
                        context = context,
                        onSuccess = {
                            mainNavController.navigateUp()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.create_quest_save_button))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text(stringResource(R.string.create_quest_title)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text(stringResource(R.string.create_quest_note)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                minLines = 2
            )

            Column {
                Text(
                    text = "Schwierigkeit",
                    modifier = Modifier.padding(bottom = 0.dp)
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp)
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ), onClick = { viewModel.updateDifficulty(index) },
                            selected = index == uiState.difficulty
                        ) { Text(label) }
                    }
                }
            }

            // Due
            Column (
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Fälligkeitsdatum",
                        modifier = Modifier.padding(bottom = 0.dp)
                    )

                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                viewModel.showDueDateInfoDialog()
                            }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            viewModel.showAddingDueDateDialog()
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val date = Date(uiState.selectedDueDate)
                            val formattedDate = dueDateFormat.format(date)

                            Icon(
                                Icons.Outlined.Schedule,
                                contentDescription = null
                            )

                            Text(
                                text = if (uiState.selectedDueDate.toInt() == 0) "Keine Fälligkeit" else formattedDate,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        if (uiState.selectedDueDate > 0) {
                            Box(
                                modifier = Modifier
                                    .clickable { viewModel.removeDueDate() }
                                    .padding(0.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                }
            }

            // Reminders
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Erinnerungen",
                    modifier = Modifier.padding(bottom = 0.dp)
                )

                uiState.notificationTriggerTimes.forEachIndexed { index, triggerTime ->
                    val date = Date(triggerTime)
                    val formattedDate = reminderDateFormat.format(date)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                viewModel.removeReminder(index)
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = null
                            )

                            Text(
                                text = formattedDate,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            viewModel.showCreateReminderDialog()
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null
                        )

                        Text(
                            text = "Erinnerung hinzufügen",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
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
    }
}

/*if (uiState.isAlertManagerInfoVisible) {
    AlertManagerInfoBottomSheet(
        sheetState = sheetState,
        onConfirm = {
            viewModel.requestExactAlarmPermission(context)
        },
        onDismiss = {

        }
    )
}*/