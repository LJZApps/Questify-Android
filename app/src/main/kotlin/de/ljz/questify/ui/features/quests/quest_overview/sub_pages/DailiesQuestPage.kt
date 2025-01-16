package de.ljz.questify.ui.features.quests.quest_overview.sub_pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.components.Placeholder
import de.ljz.questify.ui.features.quests.quest_overview.QuestOverviewViewModel

@Composable
fun DailiesQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestOverviewViewModel
) {
    LazyColumn (
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Placeholder()
        }
    }
}