package de.ljz.questify.feature.quests.presentation.screens.quest_overview.sub_pages.quest_for_category_page

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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.ljz.questify.R
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import de.ljz.questify.feature.quests.presentation.components.QuestItem

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestsForCategoryPage(
    modifier: Modifier = Modifier,
    categoryId: Int,
    onQuestClicked: (Int) -> Unit,
    onQuestChecked: (QuestEntity) -> Unit,
    onEditQuestClicked: (Int) -> Unit
) {
    val viewModel: CategoryQuestViewModel = hiltViewModel<CategoryQuestViewModel, CategoryQuestViewModel.Factory>(
        key = "category_vm_$categoryId"
    ) { factory ->
        factory.create(categoryId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val questComparator by remember(uiState.sortingDirections) {
        derivedStateOf {
            compareBy<QuestWithSubQuests> { it.quest.id }
                .let { if (uiState.sortingDirections == SortingDirections.DESCENDING) it.reversed() else it }
        }
    }

    val questList by remember(uiState.quests, uiState.showCompleted, questComparator) {
        derivedStateOf {
            uiState.quests.asSequence()
                .filter { uiState.showCompleted || !it.quest.done }
                .sortedWith(questComparator)
                .toList()
        }
    }

    if (questList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_label_off_outlined),
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

            Text(
                text = stringResource(R.string.quests_for_category_page_empty)
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    } else {
        LazyColumn(
            modifier = modifier
                .navigationBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = questList,
                key = { it.quest.id }
            ) { questWithSubQuests ->
                QuestItem(
                    questWithSubQuests = questWithSubQuests,
                    onCheckButtonClicked = {
                        onQuestChecked(questWithSubQuests.quest)
                    },
                    onEditButtonClicked = {
                        onEditQuestClicked(questWithSubQuests.quest.id)
                    },
                    onClick = {
                        onQuestClicked(questWithSubQuests.quest.id)
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}