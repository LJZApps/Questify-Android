package de.ljz.questify.ui.features.quests.quest_overview.sub_pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quest_overview.QuestOverviewViewModel

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestOverviewViewModel,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(uiState.quests.sortedBy { it.done }.asReversed(), key = { it.id }) { quest ->
            QuestItem(
                quest = quest,
                onQuestChecked = {
                    viewModel.setQuestDone(
                        quest = quest,
                        context = context,
                        onSuccess = { xp, points, level ->
                            Toast.makeText(context,  "XP: $xp, Punkte: $points" + if (level != null) ", neues Level: $level" else "", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                onClick = {
                    navController.navigate(QuestDetail(id = quest.id))
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
                modifier = Modifier
                    .animateItem() // `animateItem` für sanfte Positionsänderung - needs a key provided in "items"
                    .padding(vertical = 4.dp)
            )
        }
    }
}