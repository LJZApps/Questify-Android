package de.ljz.questify.ui.features.quests.quests_overview.sub_pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quests_overview.QuestOverviewViewModel

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    viewModel: QuestOverviewViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    if (!uiState.quests.isEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(uiState.quests.sortedBy { it.done }.asReversed(), key = { it.id }) { quest ->
                QuestItem(
                    quest = quest,
                    modifier = Modifier
                        .animateItem()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    difficultyIcon = {
                        when (quest.difficulty) {
                            Difficulty.EASY -> EasyIcon()
                            Difficulty.MEDIUM -> MediumIcon()
                            Difficulty.HARD -> HardIcon()
                            Difficulty.EPIC -> EpicIcon()
                            Difficulty.NONE -> {}
                            else -> null
                        }
                    },
                    onQuestChecked = {
                        viewModel.setQuestDone(
                            quest = quest,
                            context = context
                        )
                    },
                    onQuestDelete = {
                        viewModel.deleteQuest(it)
                    },
                    onClick = {
                        navController.navigate(QuestDetail(id = quest.id))
                    }
                )
            }
        }
    } else {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.List,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Text(stringResource(R.string.all_quests_page_empty))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}