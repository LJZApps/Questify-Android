package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppTextButton
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.core.utils.MaxWidth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditQuestScreen(
    viewModel: EditQuestViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EditQuestScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is EditQuestUiEvent.OnNavigateUp -> onNavigateUp()

                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditQuestScreen(
    uiState: EditQuestUiState,
    onUiEvent: (EditQuestUiEvent) -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd. MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateTimeFormat = SimpleDateFormat("dd. MMM yyy HH:mm", Locale.getDefault())
    val difficultyOptions = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
    )

    val haptic = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    BasicPlainTooltip(
                        text = "Zurück",
                    ) {
                        IconButton(
                            onClick = {
                                onUiEvent(EditQuestUiEvent.OnNavigateUp)
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
                    AppTextButton(
                        onClick = {

                        }
                    ) {
                        Text("Speichern")
                    }

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
                            text = { Text("Liste") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_label_filled),
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                        )

                        DropdownMenuItem(
                            text = { Text("Quest löschen") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_delete_filled),
                                    contentDescription = null,

                                )
                            },
                            onClick = {
                                dropdownExpanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.error,
                                leadingIconColor = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
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
                            onUiEvent(EditQuestUiEvent.OnTitleChanged(value = it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
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
                        value = uiState.notes?: "",
                        onValueChange = {
                            onUiEvent(EditQuestUiEvent.OnNotesChanged(value = it))
                        },
                        placeholder = { Text("Füge hier detaillierte Notizen hinzu...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 3,
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
                        val date = Date(uiState.dueDate)
                        val formattedDate = dateFormat.format(date)
                        val formattedTime = timeFormat.format(date)

                        LaunchedEffect(isFocused) {
                            if (isFocused) {
                                onUiEvent(EditQuestUiEvent.ShowAddingDueDateDialog)
                            }
                        }

                        AppOutlinedTextField(
                            value = if (uiState.dueDate == 0L) "" else formattedDate,
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
                            interactionSource = interactionSource
                        )

                        AppOutlinedTextField(
                            value = if (uiState.dueDate == 0L) "" else formattedTime,
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
                            interactionSource = interactionSource
                        )
                    }
                }
            }
        }
    }
}