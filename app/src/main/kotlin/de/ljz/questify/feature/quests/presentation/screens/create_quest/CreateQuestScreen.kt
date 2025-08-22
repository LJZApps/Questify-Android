package de.ljz.questify.feature.quests.presentation.screens.create_quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.NotificationAdd
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuCategory
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuItem
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.EpicIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.DueDateInfoDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueDateDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateQuestScreen(
    mainNavController: NavHostController,
    viewModel: CreateQuestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current

    val difficultyOptions = listOf(
        stringResource(R.string.difficulty_none),
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )

    val difficultyDescriptions = listOf(
        stringResource(R.string.difficulty_none_description, "No difficulty set"),
        stringResource(
            R.string.difficulty_easy_description,
            "Simple tasks that don't require much effort"
        ),
        stringResource(
            R.string.difficulty_medium_description,
            "Tasks that require some effort and focus"
        ),
        stringResource(
            R.string.difficulty_hard_description,
            "Challenging tasks that require significant effort"
        ),
        stringResource(
            R.string.difficulty_epic_description,
            "Major tasks that require extensive effort and time"
        )
    )

    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.create_quest_top_bar_title)) },
                subtitle = {
                    if (uiState.title.isNotBlank())
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
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.createQuest(onSuccess = { mainNavController.navigateUp() })
                }
            ) {
                Icon(Icons.Outlined.Save, contentDescription = stringResource(R.string.save))
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
                    .padding(horizontal = 16.dp), // Space for the FAB
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Title
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { viewModel.updateTitle(it) },
                    label = { Text(stringResource(R.string.text_field_quest_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )

                // Notes
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    label = { Text(stringResource(R.string.text_field_quest_note)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)) {
                    val modifiers = List(difficultyOptions.size) { Modifier.weight(1f) }
                    difficultyOptions.forEachIndexed { index, _ ->
                        ToggleButton(
                            checked = uiState.difficulty == index,
                            onCheckedChange = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                viewModel.updateDifficulty(index)
                            },
                            modifier = modifiers[index].semantics { role = Role.RadioButton },
                            shapes = when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                difficultyOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            }
                        ) {
                            val tint =
                                if (uiState.difficulty == index) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                            when (index) {
                                0 -> Icon(
                                    Icons.Outlined.Block,
                                    contentDescription = null,
                                    tint = tint
                                )

                                1 -> EasyIcon(tint = tint)
                                2 -> MediumIcon(tint = tint)
                                3 -> HardIcon(tint = tint)
                                4 -> EpicIcon(tint = tint)
                            }
                        }
                    }
                }

                ExpressiveMenuCategory(
                    title = stringResource(R.string.detailed_information_page_due_date_title),
                    content = {
                        val date = Date(uiState.selectedDueDate)
                        val formattedDate = dateFormat.format(date)
                        ExpressiveMenuItem(
                            title = if (uiState.selectedDueDate.toInt() == 0) stringResource(R.string.detailed_information_page_due_date_empty) else formattedDate,
                            icon = { Icon(Icons.Outlined.Schedule, contentDescription = null) },
                            onClick = { viewModel.showAddingDueDateDialog() }
                        )
                    }
                )

                ExpressiveMenuCategory(
                    title = stringResource(R.string.quest_detail_screen_reminders_title),
                    content = {
                        if (uiState.notificationTriggerTimes.isNotEmpty()) {
                            uiState.notificationTriggerTimes.sorted()
                                .forEachIndexed { index, triggerTime ->
                                    ExpressiveMenuItem(
                                        title = dateFormat.format(Date(triggerTime)),
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Outlined.Notifications,
                                                contentDescription = null
                                            )
                                        },
                                        onClick = {
                                            viewModel.removeReminder(index)
                                        }
                                    )
                                }
                        } else {
                            ExpressiveMenuItem(
                                title = "Keine Erinnerungen",
                                icon = {
                                    Icon(
                                        imageVector = Icons.Outlined.NotificationsOff,
                                        contentDescription = null,
                                    )
                                }
                            )
                        }

                    },
                    actionIcon = {
                        Icon(
                            imageVector = Icons.Outlined.NotificationAdd,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    onActionIconClick = {
                        viewModel.showCreateReminderDialog()
                    }
                )
            }

            // Dialogs
            if (uiState.isAddingReminder) {
                CreateReminderDialog(
                    onDismiss = viewModel::hideCreateReminderDialog,
                    onConfirm = { timestamp ->
                        viewModel.addReminder(timestamp)
                        viewModel.hideCreateReminderDialog()
                    },
                    addingDateTimeState = uiState.addingDateTimeState,
                    onReminderStateChange = { viewModel.updateReminderState(it) }
                )
            }
            if (uiState.isDueDateInfoDialogVisible) {
                DueDateInfoDialog(onDismiss = { viewModel.hideDueDateInfoDialog() })
            }
            if (uiState.isAddingDueDate) {
                SetDueDateDialog(
                    onConfirm = { dueDateTimestamp ->
                        viewModel.setDueDate(dueDateTimestamp)
                        viewModel.hideAddingDueDateDialog()
                    },
                    onDismiss = { viewModel.hideAddingDueDateDialog() },
                    addingDateTimeState = uiState.addingDateTimeState,
                    onDateTimeStateChange = { viewModel.updateReminderState(it) }
                )
            }
        }
    )
}
