package de.ljz.questify.ui.features.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                text = "Neueste Quests",
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