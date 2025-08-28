package de.ljz.questify.feature.habits.presentation.screens.create_habit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.ljz.questify.R
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

    CreateHabitScreenContent(
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
private fun CreateHabitScreenContent(
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
            TopAppBar(
                title = {
                    Text("Gewohnheit erstellen")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {

                        },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text("Erstellen")
                    }
                }
            )
        }
    ) { innerPadding ->
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
                    .padding(horizontal = 16.dp) // Behalte horizontales Padding hier
                    .widthIn(max = MaxWidth), // Setzt nur die maximale Breite
                // .fillMaxWidth() -> ENTFERNEN!
                verticalArrangement = Arrangement.spacedBy(16.dp)
                // horizontalAlignment -> wird nicht mehr benötigt, da die Kinder eh fillMaxWidth() haben
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Title,
                        contentDescription = null,
                    )

                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = {
                            onUiEvent.invoke(CreateHabitUiEvent.OnTitleChange(it))
                        },
                        label = { Text("Titel") },
                        modifier = Modifier.fillMaxWidth(),
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
                        imageVector = Icons.AutoMirrored.Default.Notes,
                        contentDescription = null,
                    )

                    OutlinedTextField(
                        value = uiState.notes,
                        onValueChange = {
                            onUiEvent.invoke(CreateHabitUiEvent.OnNotesChange(it))
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
                        text = "Typ",
                        style = MaterialTheme.typography.titleMedium
                    )

                    val selectedTypes = remember { mutableStateListOf<HabitType>() }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    ) {
                        val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1f))
                        habitTypeOptions.forEachIndexed { index, habitType ->
                            ToggleButton(
                                checked = selectedTypes.contains(habitType),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedTypes.add(habitType)
                                    } else {
                                        selectedTypes.remove(habitType)
                                    }
                                },
                                modifier = modifiers[index],
                                shapes =
                                    when (index) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        habitTypeOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                            ) {
                                Icon(
                                    imageVector = if(habitType == HabitType.POSITIVE) Icons.Default.Add else Icons.Default.Remove,
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

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    ) {
                        // Beispiel: unterschiedliche Gewichte pro Button
                        val modifiers = List(difficultyOptions.size) { Modifier.weight(1f) }

                        difficultyOptions.forEachIndexed { index, label ->
                            ToggleButton(
                                checked = 0 == index,
                                onCheckedChange = {
                                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
//                                viewModel.updateDifficulty(index)
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
                                    val tint = if (0 == index)
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
                                onCheckedChange = { selectedIndex = index },
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

                        LaunchedEffect(isFocused) {
                            if (isFocused) {
                                focusManager.clearFocus()
                                // TODO: Open time picker
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("Reset-Zeit") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (isFocused) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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