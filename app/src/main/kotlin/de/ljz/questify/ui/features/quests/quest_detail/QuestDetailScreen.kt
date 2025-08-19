package de.ljz.questify.ui.features.quests.quest_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.NotificationAdd
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.EasyIcon
import de.ljz.questify.core.presentation.components.EpicIcon
import de.ljz.questify.core.presentation.components.HardIcon
import de.ljz.questify.core.presentation.components.MediumIcon
import de.ljz.questify.core.presentation.components.expressive_menu.ExpressiveMenuCategory
import de.ljz.questify.core.presentation.components.expressive_menu.ExpressiveMenuItem
import de.ljz.questify.core.presentation.components.expressive_menu.ExpressiveMenuItemWithTextField
import de.ljz.questify.core.presentation.components.modals.CreateReminderDialog
import de.ljz.questify.ui.features.quests.create_quest.components.SetDueDateDialog
import de.ljz.questify.ui.features.quests.quest_detail.components.DeleteConfirmationDialog
import de.ljz.questify.util.NavBarConfig
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestDetailScreen(
    viewModel: QuestDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val questState = uiState.questState
    val editQuestState = uiState.editQuestState

    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val options = listOf(
        stringResource(R.string.difficulty_none),
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )

    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                },
                actions = {
                    var checked by remember { mutableStateOf(false) }

                    FilledTonalIconButton(
                        onClick = {
                            viewModel.updateQuest(
                                context = context,
                                onSuccess = {
                                    scope.launch {
                                        navController.navigateUp()
                                    }
                                }
                            )
                        },
                        shape = MaterialShapes.Sunny.toShape()
                    ) {
                        Icon(Icons.Outlined.Save, contentDescription = null)
                    }

                    FilledTonalIconButton(
                        onClick = {
                            viewModel.showDeleteConfirmationDialog()
                        },
                        shape = MaterialShapes.Ghostish.toShape(),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )

                    ) {
                        Icon(Icons.Outlined.Delete, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExpressiveMenuCategory(
                    title = stringResource(R.string.quest_detail_screen_section_informations),
                    content = {
                        ExpressiveMenuItemWithTextField(
                            text = editQuestState.title,
                            placeholder = stringResource(R.string.quest_detail_screen_description),
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Title,
                                    contentDescription = null
                                )
                            },
                            onTextValueChange = {
                                viewModel.updateTitle(it)
                            }
                        )

                        ExpressiveMenuItemWithTextField(
                            text = editQuestState.description,
                            placeholder = stringResource(R.string.quest_detail_screen_placeholder_description),
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Description,
                                    contentDescription = null
                                )
                            },
                            onTextValueChange = {
                                viewModel.updateDescription(it)
                            },
                            singleLine = false
                        )
                    }
                )

                ExpressiveMenuCategory(
                    title = stringResource(R.string.quest_detail_screen_difficulty_title),
                    content = {
                        ExpressiveMenuItem(
                            title = options[questState.difficulty],
                            icon = {
                                val tintColor = LocalContentColor.current
                                when (questState.difficulty) {
                                    0 -> {
                                        Icon(
                                            imageVector = Icons.Outlined.Block,
                                            contentDescription = null,
                                            tint = tintColor
                                        )
                                    }

                                    1 -> EasyIcon(tint = tintColor)
                                    2 -> MediumIcon(tint = tintColor)
                                    3 -> HardIcon(tint = tintColor)
                                    4 -> EpicIcon(tint = tintColor)
                                }
                            }
                        )
                    }
                )

                ExpressiveMenuCategory(
                    title = stringResource(R.string.quest_detail_screen_due_date_title),
                    content = {
                        val dueDateText = if (editQuestState.selectedDueDate == 0L) {
                            stringResource(R.string.quest_detail_screen_due_date_empty)
                        } else {
                            dateFormat.format(Date(editQuestState.selectedDueDate))
                        }

                        ExpressiveMenuItem(
                            title = dueDateText,
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = null,
                                )
                            },
                            onClick = {
                                viewModel.showDueDateSelectionDialog()
                            }
                        )
                    }
                )


                ExpressiveMenuCategory(
                    title = stringResource(R.string.quest_detail_screen_reminders_title),
                    content = {
                        if (editQuestState.notificationTriggerTimes.isNotEmpty()) {
                            editQuestState.notificationTriggerTimes.sorted()
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
                /*
                // Schwierigkeit
                Column {

                    AnimatedContent(targetState = !(uiState.isEditingQuest && questState.difficulty == 0)) { targetState ->
                        if (targetState) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val tintColor = MaterialTheme.colorScheme.primary
                                when (questState.difficulty) {
                                    0 -> {
                                        Icon(
                                            imageVector = Icons.Outlined.Block,
                                            contentDescription = null,
                                            tint = tintColor
                                        )
                                    }

                                    1 -> EasyIcon(tint = tintColor)
                                    2 -> MediumIcon(tint = tintColor)
                                    3 -> HardIcon(tint = tintColor)
                                    4 -> EpicIcon(tint = tintColor)
                                }
                                Text(
                                    text = options[questState.difficulty],
                                    modifier = Modifier.padding(start = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            Row(
                                Modifier.padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                            ) {
                                // Beispiel: unterschiedliche Gewichte pro Button
                                val modifiers = List(options.size) { Modifier.weight(1f) }

                                options.forEachIndexed { index, label ->
                                    ToggleButton(
                                        checked = editQuestState.difficulty == index,
                                        onCheckedChange = {
                                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                            viewModel.updateDifficulty(index)
                                        },
                                        modifier = modifiers[index].semantics {
                                            role = Role.RadioButton
                                        },
                                        shapes = when (index) {
                                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                            options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                        },
                                    ) {
                                        val tint = if (editQuestState.difficulty == index)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.primary

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
                        }
                    }
                }
                 */
            }

            if (uiState.isDeleteConfirmationDialogVisible) {
                DeleteConfirmationDialog(
                    onConfirm = {
                        viewModel.deleteQuest(
                            questState.questId,
                            context,
                            onSuccess = {
                                viewModel.hideDeleteConfirmationDialog()
                                navController.navigateUp()
                            }
                        )
                    },
                    onDismiss = { viewModel.hideDeleteConfirmationDialog() }
                )
            }

            if (uiState.isAddingReminder) {
                CreateReminderDialog(
                    addingDateTimeState = uiState.addingReminderDateTimeState,
                    onDismiss = { viewModel.hideCreateReminderDialog() },
                    onConfirm = {
                        viewModel.addReminder(it)
                        viewModel.hideCreateReminderDialog()
                    },
                    onReminderStateChange = {
                        viewModel.updateReminderState(it)
                    }
                )
            }

            if (uiState.isDueDateSelectionDialogVisible) {
                SetDueDateDialog(
                    onConfirm = { dueDateTimestamp ->
                        viewModel.setDueDate(dueDateTimestamp)
                        viewModel.hideDueDateSelectionDialog()
                    },
                    onDismiss = {
                        viewModel.hideDueDateSelectionDialog()
                    },
                    addingDateTimeState = uiState.addingDueDateTimeState,
                    onDateTimeStateChange = {
                        viewModel.updateDueDateState(it)
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .imePadding()
                    .navigationBarsPadding()
            )
        }
    )
}