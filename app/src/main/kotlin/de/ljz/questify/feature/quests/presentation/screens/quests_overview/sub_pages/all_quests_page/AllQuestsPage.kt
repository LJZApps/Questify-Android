package de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.all_quests_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import de.ljz.questify.feature.quests.presentation.components.QuestItem
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.AllQuestPageState

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)
@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    state: AllQuestPageState,
    onEditQuest: (Int) -> Unit,
    onQuestChecked: (QuestEntity) -> Unit,
    onQuestClicked: (Int) -> Unit,
) {
    val quests = state.quests
        .filter { quest -> state.showCompleted || !quest.quest.done }
        .sortedWith(compareBy<QuestWithSubQuests> {
            it.quest.id
        }
            .let { if (state.sortingDirections == SortingDirections.DESCENDING) it.reversed() else it })

    if (quests.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            itemsIndexed(
                items = quests,
                key = { _, questWithSubQuests -> questWithSubQuests.quest.id }
            ) { index, quest ->
                QuestItem(
                    questWithSubQuests = quest,
                    onCheckButtonClicked = {
                        onQuestChecked(quest.quest)
                    },
                    onEditButtonClicked = {
                        onEditQuest(quest.quest.id)
                    },
                    onClick = {
                        onQuestClicked(quest.quest.id)
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.navigationBarsPadding()
                        .height(0.dp)
                )
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_task_alt_outlined),
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