package de.ljz.questify.ui.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.ui.components.QuestItem

@Composable
fun LatestQuestsComponent(
    quests: List<QuestEntity>,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.latest_quests_component_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(quests.sortedBy { it.createdAt }.takeLast(3)) { quest ->
                    QuestItem(
                        quest = quest,
                        onQuestChecked = {},
                        onQuestDelete = {},
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}