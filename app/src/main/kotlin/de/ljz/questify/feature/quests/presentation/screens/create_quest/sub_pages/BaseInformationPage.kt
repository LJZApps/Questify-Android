package de.ljz.questify.feature.quests.presentation.screens.create_quest.sub_pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.core.presentation.components.EasyIcon
import de.ljz.questify.core.presentation.components.EpicIcon
import de.ljz.questify.core.presentation.components.HardIcon
import de.ljz.questify.core.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.screens.create_quest.CreateQuestUiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BaseInformationPage(
    uiState: CreateQuestUiState,
    onTitleChange: (String) -> Unit,
    onDifficultyChange: (Int) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val options = listOf(
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

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Title Section
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.quest_title_header),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Icon(
                        imageVector = Icons.Filled.Title,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { onTitleChange(it) },
                    label = { Text(stringResource(R.string.text_field_quest_title)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )
            }
        }

        // Difficulty Section
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.base_information_page_difficulty_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = stringResource(R.string.base_information_page_difficulty_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    val modifiers = List(options.size) { Modifier.weight(1f) }

                    options.forEachIndexed { index, label ->
                        OutlinedToggleButton(
                            checked = uiState.difficulty == index,
                            onCheckedChange = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onDifficultyChange(index)
                            },
                            modifier = modifiers[index].semantics {
                                role = Role.RadioButton
                            },
                            shapes = when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            },
                            colors = ToggleButtonDefaults.toggleButtonColors(
                                contentColor = if (uiState.difficulty == index)
                                    MaterialTheme.colorScheme.outlineVariant
                                else
                                    MaterialTheme.colorScheme.inverseSurface
                            )
                        ) {
                            val tint = if (uiState.difficulty == index)
                                MaterialTheme.colorScheme.inverseOnSurface
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant

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

                // Display the selected difficulty description
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        ),
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                when (uiState.difficulty) {
                                    0 -> Icon(
                                        imageVector = Icons.Outlined.Block,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )

                                    1 -> EasyIcon(tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    2 -> MediumIcon(tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    3 -> HardIcon(tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    4 -> EpicIcon(tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = options[uiState.difficulty],
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = difficultyDescriptions[uiState.difficulty],
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@UIModePreviews
@Composable
private fun BaseInformationPagePreview() {
    MaterialTheme {
        BaseInformationPage(
            uiState = CreateQuestUiState(),
            onTitleChange = {},
            onDifficultyChange = {}
        )
    }
}
