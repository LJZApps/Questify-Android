package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.bottom_sheets.ConfirmationBottomSheet
import de.ljz.questify.core.presentation.components.buttons.AppButton
import de.ljz.questify.core.presentation.components.buttons.AppTextButton
import de.ljz.questify.core.utils.MaxWidth
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.EpicIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueDateDialog
import de.ljz.questify.feature.quests.presentation.sheets.SelectCategoryBottomSheet
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
    val selectedCategory = viewModel.selectedCategory.collectAsStateWithLifecycle().value
    val categories = viewModel.categories.collectAsStateWithLifecycle().value
    val editQuestState = uiState.editQuestState
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val options = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard)
    )

    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    AppTextButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.updateQuest(
                                context = context,
                                onSuccess = {
                                    scope.launch {
                                        navController.navigateUp()
                                    }
                                }
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.save)
                        )
                    }

                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.Reject)
                            viewModel.showDeleteConfirmationDialog()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.error,
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete_outlined),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                )

                AppButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                        viewModel.createQuest()
                    },
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp, top = 4.dp)
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    Text(
                        text = "Quest abschließen"
                    )
                    /*Text(
                        text = "Erledige zuerst die Unteraufgaben, bevor du die Quest abschließen kannst",
                        textAlign = TextAlign.Center
                    )*/
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .widthIn(max = MaxWidth),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_title),
                            contentDescription = null,
                        )

                        TextField(
                            value = editQuestState.title,
                            onValueChange = {
                                viewModel.updateTitle(it)
                            },
                            label = { Text("Titel") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_notes),
                            contentDescription = null,
                        )

                        TextField(
                            value = editQuestState.description,
                            onValueChange = {
                                viewModel.updateDescription(it)
                            },
                            label = { Text("Notizen") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Schwierigkeit",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
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
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        val tint = if (editQuestState.difficulty == index)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant

                                        when (index) {
                                            0 -> EasyIcon(tint = tint)
                                            1 -> MediumIcon(tint = tint)
                                            2 -> HardIcon(tint = tint)
                                            3 -> EpicIcon(tint = tint)
                                        }

                                        Text(label)
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Liste",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isFocused: Boolean by interactionSource.collectIsFocusedAsState()

                            LaunchedEffect(isFocused) {
                                if (isFocused) {
                                    viewModel.showSelectCategoryDialog()
                                }
                            }

                            Icon(
                                painter = painterResource(R.drawable.ic_label_outlined),
                                contentDescription = null,
                            )

                            TextField(
                                value = selectedCategory?.text ?: "",
                                onValueChange = {},
                                label = { Text("Liste") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        painter = if (isFocused)
                                            painterResource(R.drawable.ic_keyboard_arrow_up)
                                        else
                                            painterResource(R.drawable.ic_keyboard_arrow_down),
                                        contentDescription = null
                                    )
                                },
                                interactionSource = interactionSource
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Zeitplanung",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isFocused: Boolean by interactionSource.collectIsFocusedAsState()
                            val date = Date(editQuestState.selectedDueDate)
                            val formattedDate = dateFormat.format(date)

                            LaunchedEffect(isFocused) {
                                if (isFocused) {
                                    viewModel.showDueDateSelectionDialog()
                                }
                            }

                            Icon(
                                painter = painterResource(R.drawable.ic_schedule_outlined),
                                contentDescription = null,
                            )

                            TextField(
                                value = if (editQuestState.selectedDueDate.toInt() == 0) "" else formattedDate,
                                onValueChange = {},
                                label = { Text("Fälligkeit") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        painter = if (isFocused)
                                            painterResource(R.drawable.ic_keyboard_arrow_up)
                                        else
                                            painterResource(R.drawable.ic_keyboard_arrow_down),
                                        contentDescription = null
                                    )
                                },
                                interactionSource = interactionSource
                            )

                            if (editQuestState.selectedDueDate.toInt() != 0) {
                                FilledTonalIconButton(
                                    onClick = {
                                        viewModel.setDueDate(0)
                                    },
                                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_remove),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Erinnerungen",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                                    .clickable {
                                        viewModel.showCreateReminderDialog()
                                    }
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = "Erinnerung hinzufügen",
                                    modifier = Modifier.size(18.dp)
                                )

                                Text("Hinzufügen")
                            }

                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            editQuestState.notificationTriggerTimes.sorted()
                                .forEachIndexed { index, triggerTime ->
                                    FilterChip(
                                        selected = false,
                                        onClick = {
                                            viewModel.removeReminder(index)
                                        },
                                        label = { Text(dateFormat.format(Date(triggerTime))) },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_notifications_filled),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(18.dp)
                                            )
                                        },
                                        colors = FilterChipDefaults.elevatedFilterChipColors()
                                    )
                                }
                        }
                    }
                }
            }

            // Dialogs
            if (uiState.dialogState == DialogState.CreateReminder) {
                CreateReminderDialog(
                    onDismiss = viewModel::hideCreateReminderDialog,
                    onConfirm = { timestamp ->
                        viewModel.addReminder(timestamp)
                        viewModel.hideCreateReminderDialog()
                    },
                    addingDateTimeState = uiState.addingReminderDateTimeState,
                    onReminderStateChange = { viewModel.updateReminderState(it) }
                )
            }

            if (uiState.dialogState == DialogState.SetDueDate) {
                SetDueDateDialog(
                    onConfirm = { dueDateTimestamp ->
                        viewModel.setDueDate(dueDateTimestamp)
                        viewModel.hideDueDateSelectionDialog()
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        viewModel.hideDueDateSelectionDialog()
                        focusManager.clearFocus()
                    },
                    addingDateTimeState = uiState.addingDueDateTimeState,
                    onDateTimeStateChange = { viewModel.updateDueDateState(it) },
                    onRemoveDueDate = {
                        viewModel.setDueDate(0)
                    }
                )
            }

            if (uiState.dialogState == DialogState.SelectCategory) {
                SelectCategoryBottomSheet(
                    categories = categories,
                    onCategorySelect = { category ->
                        viewModel.selectCategory(category)
                        viewModel.hideSelectCategoryDialog()
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        viewModel.hideSelectCategoryDialog()
                        focusManager.clearFocus()
                    },
                    onCreateCategory = { text ->
                        viewModel.addQuestCategory(text)
                    }
                )
            }

            if (uiState.dialogState == DialogState.DeleteConfirmation) {
                ConfirmationBottomSheet(
                    onDismissRequest = {
                        viewModel.hideDeleteConfirmationDialog()
                    },
                    onConfirm = {
                        viewModel.deleteQuest(
                            uiState.questId,
                            context,
                            onSuccess = {
                                viewModel.hideDeleteConfirmationDialog()
                                navController.navigateUp()
                            }
                        )
                    },
                    title = "Quest löschen?",
                    confirmationButtonText = stringResource(R.string.delete),
                    confirmationButtonColors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    dismissButtonText = stringResource(R.string.cancel),
                    text = stringResource(R.string.delete_confirmation_dialog_description),
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