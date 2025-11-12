package de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.all_quests_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    onEditQuestClicked: (Int) -> Unit,
    onQuestChecked: (QuestEntity) -> Unit,
    onQuestClicked: (Int) -> Unit,
    onCreateNewQuestButtonClicked: () -> Unit
) {
    val questComparator by remember(state.sortingDirections) {
        derivedStateOf {
            compareBy<QuestWithSubQuests> { it.quest.id }
                .let { if (state.sortingDirections == SortingDirections.DESCENDING) it.reversed() else it }
        }
    }

    val filteredQuests by remember(state.quests, state.showCompleted, questComparator) {
        derivedStateOf {
            state.quests.asSequence()
                .filter { state.showCompleted || !it.quest.done }
                .sortedWith(questComparator)
                .toList()
        }
    }

    if (filteredQuests.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(
                items = filteredQuests,
                key = { it.quest.id }
            ) { quest ->
                QuestItem(
                    questWithSubQuests = quest,
                    onCheckButtonClicked = {
                        onQuestChecked(quest.quest)
                    },
                    onEditButtonClicked = {
                        onEditQuestClicked(quest.quest.id)
                    },
                    onClick = {
                        onQuestClicked(quest.quest.id)
                    },
                    modifier = Modifier.animateItem()
                )
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
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

            Button(
                onClick = onCreateNewQuestButtonClicked
            ) {
                Text("Jetzt eine Quest erstellen")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}