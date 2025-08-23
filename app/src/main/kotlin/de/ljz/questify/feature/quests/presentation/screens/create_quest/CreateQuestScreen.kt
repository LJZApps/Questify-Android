package de.ljz.questify.feature.quests.presentation.screens.create_quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.LabelOff
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.NotificationAdd
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuCategory
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuItem
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuItemWithTextField
import de.ljz.questify.core.presentation.components.filled_tonal_icon_button.NarrowFilledTonalIconButton
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.DueDateInfoDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SelectCategoryFullscreenDialog
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
    val selectedCategory = viewModel.selectedCategory.collectAsStateWithLifecycle().value
    val categories = viewModel.categories.collectAsStateWithLifecycle().value
    val haptic = LocalHapticFeedback.current

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
                },
                actions = {
                    NarrowFilledTonalIconButton(
                        onClick = {

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
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
                },
                modifier = Modifier
                    .imePadding()
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExpressiveMenuCategory(
                    title = stringResource(R.string.quest_detail_screen_section_informations),
                    content = {
                        ExpressiveMenuItemWithTextField(
                            text = uiState.title,
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
                            text = uiState.description,
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
                    title ="Liste",
                    content = {
                        ExpressiveMenuItem(
                            title = selectedCategory?.text ?: "Keine Liste ausgewählt",
                            icon = { Icon(if (selectedCategory != null) Icons.AutoMirrored.Outlined.Label else Icons.AutoMirrored.Outlined.LabelOff, contentDescription = null) },
                            onClick = { viewModel.showSelectCategoryDialog() }
                        )
                    }
                )

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

            if (uiState.isSelectCategoryDialogVisible) {
                SelectCategoryFullscreenDialog(
                    categories = categories,
                    onCategorySelect = { category ->
                        viewModel.selectCategory(category)
                        viewModel.hideSelectCategoryDialog()
                    },
                    onDismiss = {
                        viewModel.hideSelectCategoryDialog()
                    },
                    onCreateCategory = { text ->
                        viewModel.addQuestCategory(text)
                    }
                )
            }
        }
    )
}
