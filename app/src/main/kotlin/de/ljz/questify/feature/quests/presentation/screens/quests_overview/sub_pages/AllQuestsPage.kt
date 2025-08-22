package de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.presentation.components.EasyIcon
import de.ljz.questify.feature.quests.presentation.components.EpicIcon
import de.ljz.questify.feature.quests.presentation.components.HardIcon
import de.ljz.questify.feature.quests.presentation.components.MediumIcon
import de.ljz.questify.feature.quests.presentation.components.QuestItem
import de.ljz.questify.feature.quests.presentation.screens.quest_detail.QuestDetailRoute
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.AllQuestPageState

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)
@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    state: AllQuestPageState,
    navController: NavHostController,
    onQuestDone: (QuestEntity) -> Unit,
    onQuestDelete: (Int) -> Unit,
) {
    val quests = state.quests
        .filter { quest -> state.showCompleted || !quest.done }
        .sortedWith(compareBy<QuestEntity> {
            it.id
        }
            .let { if (state.sortingDirections == SortingDirections.DESCENDING) it.reversed() else it })

    if (quests.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            itemsIndexed(
                items = quests
            ) { index, quest ->
                QuestItem(
                    quest = quest,
                    modifier = Modifier.padding(
                        top = if (index == 0) 8.dp else 1.dp,
                        bottom = 1.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    shape = RoundedCornerShape(
                        topStart = if (index == 0) 16.dp else 4.dp,
                        topEnd = if (index == 0) 16.dp else 4.dp,
                        bottomStart = if (index == quests.lastIndex) 16.dp else 4.dp,
                        bottomEnd = if (index == quests.lastIndex) 16.dp else 4.dp
                    ),
                    difficultyIcon = {
                        when (quest.difficulty) {
                            Difficulty.EASY -> EasyIcon()
                            Difficulty.MEDIUM -> MediumIcon()
                            Difficulty.HARD -> HardIcon()
                            Difficulty.EPIC -> EpicIcon()
                            Difficulty.NONE -> {}
                        }
                    },
                    onQuestChecked = {
                        onQuestDone(quest)
                    },
                    onQuestDelete = {
                        onQuestDelete(it)
                    },
                    onClick = {
                        navController.navigate(QuestDetailRoute(id = quest.id))
                    })
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.List,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialShapes.Pill.toShape()
                    )
                    .padding(16.dp)
                    .size(64.dp)
            )
            Text(stringResource(R.string.all_quests_page_empty))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}