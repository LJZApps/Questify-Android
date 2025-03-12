package de.ljz.questify.ui.features.quests.quests_overview.sub_pages

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.ui.components.EasyIcon
import de.ljz.questify.ui.components.EpicIcon
import de.ljz.questify.ui.components.HardIcon
import de.ljz.questify.ui.components.MediumIcon
import de.ljz.questify.ui.components.QuestItem
import de.ljz.questify.ui.features.quests.quest_detail.navigation.QuestDetail
import de.ljz.questify.ui.features.quests.quests_overview.AllQuestPageState
import de.ljz.questify.ui.features.quests.quests_overview.components.QuestSortingItem

@Composable
fun AllQuestsPage(
    modifier: Modifier = Modifier,
    state: AllQuestPageState,
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
            item {
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

                           /* Text(
                                text = sortOption.text,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )*/
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
            }

            items(
                state.quests
                    .filter { quest -> state.showCompleted || !quest.done }
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
                    ), key = { it.id }
            ) { quest ->
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