package de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.quest_for_category_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LabelOff
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.ljz.questify.R
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.presentation.components.QuestItem

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestsForCategoryPage(
    modifier: Modifier = Modifier,
    categoryId: Int,
    viewModel: CategoryQuestViewModel = hiltViewModel(
        key = "category_vm_$categoryId",
        creationCallback = { factory: CategoryQuestViewModel.Factory ->
            factory.create(categoryId)
        }
    ),
    onNavigateToQuestDetailScreen: (Int) -> Unit,
    onQuestDone: (QuestEntity) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator()
        }
    } else if (uiState.quests.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.LabelOff,
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
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(
                items = uiState.quests,
                key = { _, questWithSubQuests -> questWithSubQuests.quest.id }
            ) { index, questWithSubQuests ->
                QuestItem(
                    questWithSubQuests = questWithSubQuests,
                    modifier = Modifier.padding(
                        top = if (index == 0) 8.dp else 1.dp,
                        bottom = 1.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    onCheckButtonClicked = {
                        onQuestDone(questWithSubQuests.quest)
                    },
                    onEditButtonClicked = {

                    },
                    onClick = {
                        onNavigateToQuestDetailScreen(questWithSubQuests.quest.id)
                    }
                )
            }
        }
    }
}