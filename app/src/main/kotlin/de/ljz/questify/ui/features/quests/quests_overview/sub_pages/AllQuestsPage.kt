package de.ljz.questify.ui.features.quests.quests_overview.sub_pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.core.presentation.components.EasyIcon
import de.ljz.questify.core.presentation.components.EpicIcon
import de.ljz.questify.core.presentation.components.HardIcon
import de.ljz.questify.core.presentation.components.MediumIcon
import de.ljz.questify.core.presentation.components.QuestItem
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quests_overview.AllQuestPageState
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestSortingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    state: AllQuestPageState,
    searchBarState: SearchBarState,
    textFieldState: TextFieldState,
    navController: NavHostController,
    onQuestDone: (QuestEntity) -> Unit,
    onQuestDelete: (Int) -> Unit,
    onSortButtonClick: () -> Unit,
) {
    val sortingOptions = listOf(
        QuestSortingItem(
            stringResource(R.string.quest_sorting_default),
            QuestSorting.ID,
            Icons.Default.FormatListNumbered
        ),
        QuestSortingItem(
            stringResource(R.string.quest_sorting_title),
            QuestSorting.TITLE,
            Icons.Default.Title
        ),
        QuestSortingItem(
            stringResource(R.string.quest_sorting_notes),
            QuestSorting.NOTES,
            Icons.Default.Notes
        ),
        QuestSortingItem(
            stringResource(R.string.quest_sorting_due_date),
            QuestSorting.DUE_DATE,
            Icons.Default.DateRange
        ),
        QuestSortingItem(
            stringResource(R.string.quest_sorting_quest_complete),
            QuestSorting.DONE,
            Icons.Default.CheckCircle
        ),
    )

    if (state.quests.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            /*item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Sortierung anzeigen mit visueller Verbesserung
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        // Sortierrichtung-Icon mit Animation
                        val sortTransition = updateTransition(
                            targetState = state.sortingDirections,
                            label = "SortDirectionTransition"
                        )
                        val iconRotation by sortTransition.animateFloat(
                            label = "IconRotation",
                            transitionSpec = { tween(durationMillis = 300) }
                        ) { direction ->
                            when (direction) {
                                SortingDirections.ASCENDING -> 0f
                                SortingDirections.DESCENDING -> 180f
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = iconRotation
                            }
                        )

                        // Sortieroption mit Icon
                        val sortOption = sortingOptions[state.sortingBy.ordinal]
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Icon der aktuellen Sortiermethode
                            Icon(
                                imageVector = sortOption.icon, // Du musst deine sortingOptions mit Icons erweitern
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Verbesserter Sortier-Button
                    IconButton(
                        onClick = { onSortButtonClick() },
                        modifier = Modifier.size(36.dp),  // Eine kleinere Größe setzen
                        interactionSource = remember { MutableInteractionSource() },
                        content = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Sortierung ändern",
                                    modifier = Modifier.size(20.dp)  // Icon-Größe reduzieren
                                )
                            }
                        }
                    )
                }
            }*/

            itemsIndexed(
                items = state.quests
                    .filter { quest -> state.showCompleted || !quest.done }
                    .filter { quest ->
                        if (searchBarState.currentValue == SearchBarValue.Expanded) quest.title.contains(textFieldState.text, ignoreCase = true) || quest.notes?.contains(textFieldState.text, ignoreCase = true) == true else true
                    }
                    .sortedWith(
                        compareBy<QuestEntity> {
                            when (state.sortingBy) {
                                QuestSorting.DONE -> it.done
                                QuestSorting.TITLE -> it.title
                                QuestSorting.NOTES -> it.notes
                                QuestSorting.DUE_DATE -> it.dueDate
                                else -> it.id
                            }
                        }.let { if (state.sortingDirections == SortingDirections.DESCENDING) it.reversed() else it }
                    )
            ) { index, quest ->
                QuestItem(
                    quest = quest,
                    modifier = Modifier
                        .animateItem()
                        .padding(top = if (index == 0) 8.dp else 1.dp, bottom = 1.dp, start = 16.dp, end = 16.dp),
                    shape = if (index == 0) RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 4.dp, bottomEnd = 4.dp) else if (index == state.quests.lastIndex) RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp, topStart = 4.dp, topEnd = 4.dp) else RoundedCornerShape(4.dp),
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
                        navController.navigate(QuestDetail(id = quest.id))
                    }
                )
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Text(stringResource(R.string.all_quests_page_empty))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}