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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
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
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppButton
import de.ljz.questify.core.presentation.components.buttons.AppOutlinedButton
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import de.ljz.questify.core.utils.MaxWidth
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.EpicIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.dialogs.CreateReminderDialog
import de.ljz.questify.feature.quests.presentation.dialogs.DueDateInfoDialog
import de.ljz.questify.feature.quests.presentation.dialogs.SetDueDateDialog
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

    val dateFormat = SimpleDateFormat("dd. MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
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
                    IconButton(
                        onClick = {
                            dropdownExpanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Liste") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Label,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                /*dropdownExpanded = false
                                onUiEvent(QuestOverviewUiEvent.ShowDialog(DialogState.SortingBottomSheet))*/
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clip(RoundedCornerShape(10.dp))
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
                        .navigationBarsPadding()
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
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Gib den Titel deiner Quest ein..."
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            ),
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
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            ),
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
                            val interactionSource = remember { MutableInteractionSource() }
                            val isFocused: Boolean by interactionSource.collectIsFocusedAsState()
                            val date = Date(uiState.selectedDueDate)
                            val formattedDate = dateFormat.format(date)
                            val formattedTime = timeFormat.format(date)

                            LaunchedEffect(isFocused) {
                                if (isFocused) {
                                    viewModel.showAddingDueDateDialog()
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
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = null
                                    )
                                },
                                interactionSource = interactionSource
                            )

                            AppOutlinedTextField(
                                value = if (uiState.selectedDueDate.toInt() == 0) "" else formattedTime,
                                onValueChange = {},
                                modifier = Modifier.weight(1f),
                                placeholder = {
                                    Text(text = "Zeit")
                                },
                                singleLine = true,
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = null
                                    )
                                },
                                interactionSource = interactionSource
                            )

                            /*if (uiState.selectedDueDate.toInt() != 0) {
                                IconButton(
                                    onClick = {
                                        viewModel.removeDueDate()
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = null
                                    )
                                }
                            }*/
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
                                        label = { Text(dateFormat.format(Date(triggerTime))) },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Notifications,
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
                                    imageVector = Icons.Outlined.Add,
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
                            // Beispiel: unterschiedliche Gewichte pro Button
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
                            text = "Unteraufgaben",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedCard(
                                onClick = {

                                },
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
                                    Text(
                                        text = "Unteraufgaben hinzufügen"
                                    )

                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.DeleteOutline,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }

                            OutlinedCard(
                                onClick = {

                                },
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
                                    Text(
                                        text = "Unteraufgaben hinzufügen"
                                    )

                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.DeleteOutline,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }

                            OutlinedCard(
                                onClick = {

                                },
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
                                    val interactionSource = remember { MutableInteractionSource() }
                                    var subTaskText by remember { mutableStateOf("") }

                                    BasicTextField(
                                        value = subTaskText,
                                        onValueChange = {
                                            subTaskText = it
                                        },
                                        modifier = Modifier.weight(1f),
                                        textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                                        decorationBox = @Composable { innerTextField ->
                                            TextFieldDefaults.DecorationBox(
                                                value = subTaskText,
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
                                                contentPadding = PaddingValues(0.dp),
                                            )
                                        }
                                    )

                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.DeleteOutline,
                                            contentDescription = null
                                        )
                                    }
                                }
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
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = null
                                )

                                Text("Unteraufgabe hinzufügen")
                            }
                        }
                    }

                    /*Column(
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
                                imageVector = Icons.AutoMirrored.Outlined.Label,
                                contentDescription = null,
                            )

                            OutlinedTextField(
                                value = selectedCategory?.text ?: "",
                                onValueChange = {},
                                label = { Text("Liste") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = if (isFocused) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = null
                                    )
                                },
                                shape = RoundedCornerShape(6.dp),
                                interactionSource = interactionSource
                            )
                        }
                    }*/
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

            if (uiState.isAddingDueDate) {
                SetDueDateDialog(
                    onConfirm = { dueDateTimestamp ->
                        viewModel.setDueDate(dueDateTimestamp)
                        viewModel.hideAddingDueDateDialog()
                        focusManager.clearFocus()
                    },
                    onDismiss = {
                        viewModel.hideAddingDueDateDialog()
                        focusManager.clearFocus()
                    },
                    addingDateTimeState = uiState.addingDateTimeState,
                    onDateTimeStateChange = { viewModel.updateReminderState(it) }
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
