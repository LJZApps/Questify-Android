package de.ljz.questify.ui.features.quests.viewquests.subpages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.features.quests.viewquests.QuestsViewModel

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestsViewModel,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val quests = uiState.quests

    LazyColumn {
        items(quests) { quest ->
            QuestItem(
                id = quest.id,
                title = quest.title,
                description = quest.description,
                done = quest.done,
                onQuestChecked = {
                    viewModel.setQuestDone(quest.id, true)
                },
                navController = navController
            )
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}