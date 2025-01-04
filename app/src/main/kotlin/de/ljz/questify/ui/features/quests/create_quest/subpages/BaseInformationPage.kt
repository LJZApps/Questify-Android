package de.ljz.questify.ui.features.quests.create_quest.subpages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.features.quests.create_quest.CreateQuestUiState

@Composable
fun BaseInformationPage(
    uiState: CreateQuestUiState,
    onTitleChange: (String) -> Unit,
    onDifficultyChange: (Int) -> Unit
) {
    val options = listOf(
        stringResource(R.string.difficulty_easy),
        stringResource(R.string.difficulty_medium),
        stringResource(R.string.difficulty_hard),
        stringResource(R.string.difficulty_epic)
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = { onTitleChange(it) },
            label = { Text(stringResource(R.string.create_quest_title)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Schwierigkeit",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    options.forEachIndexed { index, label ->
                        val selected = (index == uiState.difficulty)

                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = { onDifficultyChange(index) },
                            selected = selected,
                            icon = {}
                        ) {
                            val tintColor = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.primary

                            when (index) {
                                0 -> EasyIcon(tint = tintColor)
                                1 -> MediumIcon(tint = tintColor)
                                2 -> HardIcon(tint = tintColor)
                                3 -> EpicIcon(tint = tintColor)
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