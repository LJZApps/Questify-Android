package de.ljz.questify.ui.features.quests.create_quest.sub_pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.ds.components.GameTextField
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.scroll
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestUiState

@Composable
fun BaseInformationPage(
    uiState: CreateQuestUiState,
    onTitleChange: (String) -> Unit,
    onDifficultyChange: (Int) -> Unit
) {
    val options = listOf(
        stringResource(R.string.difficulty_none),
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Game-themed text field
        GameTextField(
            value = uiState.title,
            onValueChange = { onTitleChange(it) },
            label = stringResource(R.string.text_field_quest_title),
            placeholder = stringResource(R.string.text_field_quest_title),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        // Game-themed card with scroll shape
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, MaterialTheme.shapes.scroll)
                .clip(MaterialTheme.shapes.scroll)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.scroll
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    shape = MaterialTheme.shapes.scroll
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.base_information_page_difficulty_title),
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                // Game-themed difficulty selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    options.forEachIndexed { index, label ->
                        val selected = (index == uiState.difficulty)
                        val interactionSource = remember { MutableInteractionSource() }

                        // Each difficulty option
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .height(64.dp)
                                .shadow(
                                    elevation = if (selected) 4.dp else 1.dp,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    color = if (selected) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (selected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = { onDifficultyChange(index) }
                                )
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            val tintColor = if (selected) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            }

                            // Difficulty icon
                            when (index) {
                                0 -> {
                                    Icon(
                                        imageVector = Icons.Outlined.Block,
                                        contentDescription = label,
                                        tint = tintColor
                                    )
                                }
                                1 -> EasyIcon(tint = tintColor)
                                2 -> MediumIcon(tint = tintColor)
                                3 -> HardIcon(tint = tintColor)
                                4 -> EpicIcon(tint = tintColor)
                            }

                            // Difficulty label
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = tintColor
                            )
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
    GameTheme {
        BaseInformationPage(
            uiState = CreateQuestUiState(),
            onTitleChange = {},
            onDifficultyChange = {}
        )
    }
}
