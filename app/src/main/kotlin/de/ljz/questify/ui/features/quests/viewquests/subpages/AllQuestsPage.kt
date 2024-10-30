package de.ljz.questify.ui.features.quests.viewquests.subpages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.features.quests.viewquests.ViewQuestsViewModel

enum class SortType { DONE, TITLE, DIFFICULTY }

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: ViewQuestsViewModel,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

    // Animierte LazyColumn mit sortierten Items
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(uiState.quests, key = { it.id }) { quest ->
            QuestItem(
                id = quest.id,
                title = quest.title,
                description = quest.description,
                done = quest.done,
                onQuestChecked = {
                    viewModel.setQuestDone(quest.id, !quest.done)
                },
                difficultyIcon = {
                    when (quest.difficulty) {
                        Difficulty.EASY -> EasyIcon()
                        Difficulty.MEDIUM -> MediumIcon()
                        Difficulty.HARD -> HardIcon()
                        Difficulty.EPIC -> EpicIcon()
                        else -> null
                    }
                },
                navController = navController,
                modifier = Modifier
                    .animateItem() // `animateItem` für sanfte Positionsänderung
                    .padding(vertical = 4.dp)
            )
        }
    }
}