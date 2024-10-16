package de.ljz.questify.ui.features.quests.subpages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import de.ljz.questify.ui.features.quests.QuestsViewModel

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestsViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    val quests = uiState.quests

    LazyColumn {
        items(quests) { quest ->
            QuestItem(
                title = quest.title,
                description = quest.description,
                done = quest.done,
                onQuestChecked = {
                    viewModel.setQuestDone(quest.id, true)
                }
            )
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}