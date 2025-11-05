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
import androidx.compose.material3.Button
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppOutlinedButton
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.core.utils.MaxWidth
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueDateDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueTimeDialog
import de.ljz.questify.feature.quests.presentation.sheets.SelectCategoryBottomSheet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateQuestScreen(
    viewModel: CreateQuestViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffects.collect { effect ->
            when (effect) {
                is CreateQuestUiEffect.OnNavigateUp -> onNavigateBack()
            }
        }
    }

    CreateQuestScreen(
        uiState = uiState,
        selectedCategory = selectedCategory,
        categories = categories,
        onUiEvent = { event ->
            when (event) {
                is CreateQuestUiEvent.OnNavigateUp -> {
                    onNavigateBack()
                }

                else -> viewModel.onUiEvent(event = event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun CreateQuestScreen(
    uiState: CreateQuestUiState,
    selectedCategory: QuestCategoryEntity?,
    categories: List<QuestCategoryEntity>,
    onUiEvent: (CreateQuestUiEvent) -> Unit
) {
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

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
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
                            onClick = {
                                onUiEvent(CreateQuestUiEvent.OnNavigateUp)
                            },
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
                                onUiEvent(CreateQuestUiEvent.OnShowDialog(DialogState.SelectCategorySheet))
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

                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onUiEvent(CreateQuestUiEvent.OnCreateQuest)
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
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
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

                        OutlinedTextField(
                            value = uiState.title,
                            onValueChange = {
                                onUiEvent(CreateQuestUiEvent.OnTitleUpdated(it))
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

                        OutlinedTextField(
                            value = uiState.description,
                            onValueChange = {
                                onUiEvent(CreateQuestUiEvent.OnDescriptionUpdated(it))
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
                            val isTimeFocused: Boolean by timeInteractionSource.collectIsFocusedAsState()

                            val date = Date(uiState.selectedDueDate)
                            val formattedDate = dateFormat.format(date)
                            val formattedTime = timeFormat.format(date)

                            LaunchedEffect(isDateFocused) {
                                if (isDateFocused) {
                                    onUiEvent(CreateQuestUiEvent.OnShowDialog(DialogState.DatePicker))
                                }
                            }

                            LaunchedEffect(isTimeFocused) {
                                if (isTimeFocused) {
                                    onUiEvent(CreateQuestUiEvent.OnShowDialog(DialogState.TimePicker))
                                }
                            }

                            OutlinedTextField(
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

                            OutlinedTextField(
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
                                            onUiEvent(CreateQuestUiEvent.OnRemoveReminder(index = index))
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
                                onUiEvent(CreateQuestUiEvent.OnShowDialog(DialogState.AddReminder))
                            },
                            modifier = Modifier.fillMaxWidth(),
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
                                        onUiEvent(CreateQuestUiEvent.OnDifficultyUpdated(value = index))
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
                                                onUiEvent(
                                                    CreateQuestUiEvent.OnUpdateSubQuest(
                                                        index = index,
                                                        value = it
                                                    )
                                                )
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
                                                onUiEvent(CreateQuestUiEvent.OnCreateSubQuest)
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
                                                if ((uiState.subTasks.count() - 1) > 0) {
                                                    subTaskFocusManager.moveFocus(FocusDirection.Previous)
                                                } else {
                                                    subTaskFocusManager.clearFocus()
                                                }

                                                onUiEvent(CreateQuestUiEvent.OnRemoveSubQuest(index))
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
                                onUiEvent(CreateQuestUiEvent.OnCreateSubQuest)
                            },
                            modifier = Modifier.fillMaxWidth(),
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
            if (uiState.dialogState is DialogState.AddReminder) {
                CreateReminderDialog(
                    onDismiss = {
                        onUiEvent(CreateQuestUiEvent.OnCloseDialog)
                    },
                    onConfirm = { timestamp ->
                        onUiEvent(CreateQuestUiEvent.OnCreateReminder(timestamp = timestamp))
                        onUiEvent(CreateQuestUiEvent.OnCloseDialog)
                    },
                    addingDateTimeState = uiState.addingDateTimeState,
                    onReminderStateChange = {
                        onUiEvent(CreateQuestUiEvent.OnUpdateReminderState(it))
                    }
                )
            }

            val initialDateTimeMillis = uiState.selectedDueDate.takeIf { it != 0L }

            if (uiState.dialogState is DialogState.DatePicker) {
                SetDueDateDialog(
                    onConfirm = { timestamp ->
                        onUiEvent(CreateQuestUiEvent.OnSetDueDate(timestamp = timestamp))
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        onUiEvent(CreateQuestUiEvent.OnCloseDialog)
                        focusManager.clearFocus()
                    },
                    onRemoveDueDate = {
                        onUiEvent(CreateQuestUiEvent.OnRemoveDueDate)
                        focusManager.clearFocus()
                    },
                    initialSelectedDateTimeMillis = initialDateTimeMillis
                )
            }

            if (uiState.dialogState is DialogState.TimePicker) {
                SetDueTimeDialog(
                    onConfirm = { timestamp ->
                        onUiEvent(CreateQuestUiEvent.OnSetDueDate(timestamp = timestamp))
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        onUiEvent(CreateQuestUiEvent.OnCloseDialog)
                        focusManager.clearFocus()
                    },
                    onRemoveDueDate = {
                        onUiEvent(CreateQuestUiEvent.OnRemoveDueDate)
                        focusManager.clearFocus()
                    },
                    initialSelectedDateTimeMillis = initialDateTimeMillis
                )
            }

            if (uiState.dialogState is DialogState.SelectCategorySheet) {
                SelectCategoryBottomSheet(
                    categories = categories,
                    onCategorySelect = { category ->
                        onUiEvent(CreateQuestUiEvent.OnSelectQuestCategory(category))
                        onUiEvent(CreateQuestUiEvent.OnCloseDialog)
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        onUiEvent(CreateQuestUiEvent.OnCloseDialog)
                        focusManager.clearFocus()
                    },
                    onCreateCategory = { text ->
                        onUiEvent(CreateQuestUiEvent.OnCreateQuestCategory(value = text))
                    }
                )
            }
        }
    )
}
