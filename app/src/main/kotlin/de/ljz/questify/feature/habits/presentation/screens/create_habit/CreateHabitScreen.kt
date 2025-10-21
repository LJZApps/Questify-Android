package de.ljz.questify.feature.habits.presentation.screens.create_habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.buttons.AppButton
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import de.ljz.questify.core.presentation.components.tooltips.BasicPlainTooltip
import de.ljz.questify.core.utils.MaxWidth
import de.ljz.questify.feature.habits.data.models.HabitType
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.EpicIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    CreateHabitScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CreateHabitScreen(
    uiState: CreateHabitUiState,
    onUiEvent: (CreateHabitUiEvent) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current

    val habitTypeOptions = HabitType.entries.toTypedArray()
    val counterResetOptions = listOf("Täglich", "Wöchentlich", "Monatlich")
    val difficultyOptions = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Gewohnheit erstellen",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    BasicPlainTooltip(
                        text = "Zurück"
                    ) {
                        IconButton(
                            onClick = { },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp, top = 4.dp)
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    Text(
                        text = "Gewohnheit erstellen"
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Titel",
                        style = MaterialTheme.typography.titleMedium
                    )

                    AppOutlinedTextField(
                        value = uiState.title,
                        onValueChange = {
                            onUiEvent.invoke(CreateHabitUiEvent.OnTitleChange(it))
                        },
                        placeholder = {
                            Text(
                                text = "Gib den Titel deiner Gewohnheit ein..."
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Notizen",
                        style = MaterialTheme.typography.titleMedium
                    )

                    AppOutlinedTextField(
                        value = uiState.notes,
                        onValueChange = {
                            onUiEvent.invoke(CreateHabitUiEvent.OnNotesChange(it))
                        },
                        placeholder = { Text("Füge hier detaillierte Notizen hinzu...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Typ",
                        style = MaterialTheme.typography.titleMedium
                    )

                    var selectedType by remember { mutableStateOf(HabitType.POSITIVE) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    ) {
                        habitTypeOptions.forEachIndexed { index, habitType ->
                            ToggleButton(
                                checked = selectedType == habitType,
                                onCheckedChange = {
                                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                    selectedType = habitType
                                },
                                modifier = Modifier.weight(1f),
                                shapes =
                                    when (index) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        habitTypeOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                            ) {
                                Icon(
                                    imageVector = if (habitType == HabitType.POSITIVE) Icons.Default.Add else Icons.Default.Remove,
                                    contentDescription = null,
                                )
                                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                                Text(
                                    text = if (habitType == HabitType.POSITIVE) "Positiv" else "Negativ"
                                )
                            }
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

                    var selectedDifficulty by remember { mutableIntStateOf(0) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    ) {
                        difficultyOptions.forEachIndexed { index, label ->
                            ToggleButton(
                                checked = selectedDifficulty == index,
                                onCheckedChange = {
                                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                    selectedDifficulty = index
//                                viewModel.updateDifficulty(index)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
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
                                    val tint = if (selectedDifficulty == index)
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
                        text = "Zähler zurücksetzen",
                        style = MaterialTheme.typography.titleMedium
                    )

                    var selectedIndex by remember { mutableIntStateOf(0) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    ) {
                        counterResetOptions.forEachIndexed { index, label ->
                            ToggleButton(
                                checked = selectedIndex == index,
                                onCheckedChange = {
                                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                    selectedIndex = index
                                },
                                modifier = Modifier.weight(1f),
                                shapes =
                                    when (index) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        counterResetOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                            ) {
                                Text(label)
                            }
                        }
                    }
                }
            }
        }
    }
}