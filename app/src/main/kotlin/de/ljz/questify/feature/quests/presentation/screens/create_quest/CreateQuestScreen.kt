package de.ljz.questify.feature.quests.presentation.screens.create_quest

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppButton
import de.ljz.questify.core.presentation.components.buttons.AppOutlinedButton
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.core.utils.MaxWidth
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.DueDateInfoDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueDateDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueTimeDialog
import de.ljz.questify.feature.quests.presentation.sheets.SelectCategoryBottomSheet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateQuestScreen(
    mainNavController: NavHostController,
    viewModel: CreateQuestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val questCreationSucceeded by viewModel.questCreationSucceeded.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    var dropdownExpanded by remember { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val dateFormat = SimpleDateFormat("dd. MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateTimeFormat = SimpleDateFormat("dd. MMM yyy HH:mm", Locale.getDefault())
    val difficultyOptions = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
    )

    LaunchedEffect(questCreationSucceeded) {
        if (questCreationSucceeded) {
            mainNavController.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.create_quest_top_bar_title,
                        ),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    BasicPlainTooltip(
                        text = "Zurück",
                    ) {
                        IconButton(
                            onClick = { mainNavController.navigateUp() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                },
                actions = {
                    BasicPlainTooltip(
                        text = "Mehr",
                        position = TooltipAnchorPosition.Below
                    ) {
                        IconButton(
                            onClick = {
                                dropdownExpanded = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more_vert),
                                contentDescription = null
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = selectedCategory?.text ?: "Liste"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_label_filled),
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                                viewModel.showSelectCategoryDialog()
//                                onUiEvent(QuestOverviewUiEvent.ShowDialog(DialogState.SortingBottomSheet))
                            }
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
                        viewModel.createQuest()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp, top = 4.dp)
                        .imePadding()
                        .navigationBarsPadding(),
                    enabled = uiState.title.trim().isNotEmpty()
                ) {
                    Text(
                        text = "Quest erstellen"
                    )
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .widthIn(max = MaxWidth),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Titel",
                            style = MaterialTheme.typography.titleMedium
                        )

                        AppOutlinedTextField(
                            value = uiState.title,
                            onValueChange = {
                                viewModel.updateTitle(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            placeholder = {
                                Text(
                                    text = "Gib den Titel deiner Quest ein..."
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Notizen",
                            style = MaterialTheme.typography.titleMedium
                        )

                        AppOutlinedTextField(
                            value = uiState.description,
                            onValueChange = {
                                viewModel.updateDescription(it)
                            },
                            placeholder = { Text("Füge hier detaillierte Notizen hinzu...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            maxLines = 3,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                            )
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Fälligkeitsdatum & Zeit",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val dateInteractionSource = remember { MutableInteractionSource() }
                            val isDateFocused: Boolean by dateInteractionSource.collectIsFocusedAsState()
                            val timeInteractionSource = remember { MutableInteractionSource() }
                            val isTimeFocused : Boolean by timeInteractionSource.collectIsFocusedAsState()

                            val date = Date(uiState.selectedDueDate)
                            val formattedDate = dateFormat.format(date)
                            val formattedTime = timeFormat.format(date)

                            LaunchedEffect(isDateFocused) {
                                if (isDateFocused) {
                                    viewModel.showDatePickerDialog()
                                }
                            }

                            LaunchedEffect(isTimeFocused) {
                                if (isTimeFocused) {
                                    viewModel.showTimePickerDialog()
                                }
                            }

                            AppOutlinedTextField(
                                value = if (uiState.selectedDueDate.toInt() == 0) "" else formattedDate,
                                onValueChange = {},
                                modifier = Modifier.weight(2f),
                                placeholder = {
                                    Text(text = "Datum")
                                },
                                singleLine = true,
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_calendar_today_filled),
                                        contentDescription = null
                                    )
                                },
                                interactionSource = dateInteractionSource
                            )

                            AppOutlinedTextField(
                                value = if (uiState.selectedDueDate == 0L) "" else formattedTime,
                                onValueChange = {},
                                modifier = Modifier.weight(1f),
                                placeholder = {
                                    Text(text = "Zeit")
                                },
                                singleLine = true,
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_schedule_outlined),
                                        contentDescription = null
                                    )
                                },
                                interactionSource = timeInteractionSource
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Erinnerungen",
                            style = MaterialTheme.typography.titleMedium
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            uiState.notificationTriggerTimes.sorted()
                                .forEachIndexed { index, triggerTime ->
                                    FilterChip(
                                        selected = false,
                                        onClick = {
                                            viewModel.removeReminder(index)
                                        },
                                        label = { Text(dateTimeFormat.format(Date(triggerTime))) },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_notifications_outlined),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(18.dp)
                                            )
                                        },
                                        colors = FilterChipDefaults.elevatedFilterChipColors()
                                    )
                                }
                        }

                        AppOutlinedButton(
                            onClick = {
                                viewModel.showCreateReminderDialog()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = null
                                )

                                Text("Erinnerung hinzufügen")
                            }
                        }
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
                            val modifiers = List(difficultyOptions.size) { Modifier.weight(1f) }

                            difficultyOptions.forEachIndexed { index, label ->
                                ToggleButton(
                                    checked = uiState.difficulty == index,
                                    onCheckedChange = {
                                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                        viewModel.updateDifficulty(index)
                                    },
                                    modifier = modifiers[index].semantics {
                                        role = Role.RadioButton
                                    },
                                    shapes = when (index) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        difficultyOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        val tint = if (uiState.difficulty == index)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant

                                        when (index) {
                                            0 -> EasyIcon(tint = tint)
                                            1 -> MediumIcon(tint = tint)
                                            2 -> HardIcon(tint = tint)
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
                            text = "Unteraufgaben",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.subTasks.forEachIndexed { index, subTask ->
                                val subTaskFocusManager = LocalFocusManager.current
                                val subTaskFocusRequester = remember { FocusRequester() }

                                LaunchedEffect(index) {
                                    subTaskFocusRequester.requestFocus()
                                }

                                OutlinedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 16.dp,
                                                vertical = 8.dp
                                            )
                                            .fillMaxWidth()
                                    ) {
                                        val interactionSource =
                                            remember { MutableInteractionSource() }

                                        BasicTextField(
                                            value = subTask.text,
                                            onValueChange = {
                                                viewModel.updateSubtask(index, it)
                                            },
                                            modifier = Modifier
                                                .weight(1f)
                                                .focusRequester(subTaskFocusRequester),
                                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            ),
                                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                                            singleLine = true,
                                            maxLines = 1,
                                            keyboardOptions = KeyboardOptions(
                                                imeAction = ImeAction.Next,
                                                capitalization = KeyboardCapitalization.Sentences
                                            ),
                                            keyboardActions = KeyboardActions {
                                                viewModel.addSubTask()
                                            },
                                            decorationBox = @Composable { innerTextField ->
                                                TextFieldDefaults.DecorationBox(
                                                    value = subTask.text,
                                                    enabled = true,
                                                    innerTextField = innerTextField,
                                                    singleLine = true,
                                                    visualTransformation = VisualTransformation.None,
                                                    colors = TextFieldDefaults.colors(
                                                        focusedIndicatorColor = Color.Transparent,
                                                        unfocusedIndicatorColor = Color.Transparent,
                                                        disabledIndicatorColor = Color.Transparent,
                                                        focusedContainerColor = Color.Transparent,
                                                        unfocusedContainerColor = Color.Transparent,
                                                        disabledContainerColor = Color.Transparent,
                                                    ),
                                                    interactionSource = interactionSource,
                                                    placeholder = {
                                                        Text(
                                                            text = "Text hier eingeben"
                                                        )
                                                    },
                                                    contentPadding = PaddingValues(0.dp)
                                                )
                                            }
                                        )

                                        IconButton(
                                            onClick = {
                                                viewModel.removeSubtask(index)
                                                if (uiState.subTasks.count() > 0) {
                                                    subTaskFocusManager.moveFocus(FocusDirection.Previous)
                                                } else {
                                                    subTaskFocusManager.clearFocus()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_delete_outlined),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        AppOutlinedButton(
                            onClick = {
                                viewModel.addSubTask()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = null
                                )

                                Text("Unteraufgabe hinzufügen")
                            }
                        }
                    }
                }
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

            val initialDateTimeMillis = uiState.selectedDueDate.takeIf { it != 0L }

            if (uiState.isDatePickerDialogVisible) {
                SetDueDateDialog(
                    onConfirm = { timestamp ->
                        viewModel.setDueDate(timestamp)
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        viewModel.hideDatePickerDialog()
                        focusManager.clearFocus()
                    },
                    onRemoveDueDate = {
                        viewModel.removeDueDate()
                        focusManager.clearFocus()
                    },
                    initialSelectedDateTimeMillis = initialDateTimeMillis
                )
            }

            if (uiState.isTimePickerDialogVisible) {
                SetDueTimeDialog(
                    onConfirm = { timestamp ->
                        viewModel.setDueDate(timestamp)
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        viewModel.hideTimePickerDialog()
                        focusManager.clearFocus()
                    },
                    onRemoveDueDate = {
                        viewModel.removeDueDate()
                        focusManager.clearFocus()
                    },
                    initialSelectedDateTimeMillis = initialDateTimeMillis
                )
            }

            if (uiState.isSelectCategoryDialogVisible) {
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
        }
    )
}
