package de.ljz.questify.ui.features.quests.quests_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.ui.features.quests.quests_overview.SortingDirectionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestSortingBottomSheet(
    onDismiss: () -> Unit,
    questSorting: QuestSorting,
    sortingDirection: SortingDirections,
    showCompletedQuests: Boolean,
    onSortingChanged: (QuestSorting) -> Unit,
    onSortingDirectionChanged: (SortingDirections) -> Unit,
    onShowCompletedQuestsChanged: (Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    val sortingOptions = listOf(
        QuestSortingItem(stringResource(R.string.quest_sorting_default), QuestSorting.ID, Icons.Default.FormatListNumbered),
        QuestSortingItem(stringResource(R.string.quest_sorting_title), QuestSorting.TITLE, Icons.Default.Title),
        QuestSortingItem(stringResource(R.string.quest_sorting_notes), QuestSorting.NOTES, Icons.Default.Notes),
        QuestSortingItem(stringResource(R.string.quest_sorting_due_date), QuestSorting.DUE_DATE, Icons.Default.DateRange),
//        QuestSortingItem(stringResource(R.string.quest_sorting_quest_complete), QuestSorting.DONE, Icons.Default.CheckCircle),
    ) + listOfNotNull(
        if (showCompletedQuests) QuestSortingItem(
            stringResource(R.string.quest_sorting_quest_complete),
            QuestSorting.DONE,
            Icons.Default.CheckCircle
        ) else null
    )

    val sortingDirections = listOf(
        SortingDirectionItem(stringResource(R.string.sorting_direction_ascending), SortingDirections.ASCENDING),
        SortingDirectionItem(stringResource(R.string.sorting_direction_descending), SortingDirections.DESCENDING)
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
            onDismiss()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.quest_sorting_bottom_sheet_title),
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.quest_sorting_bottom_sheet_sort_by),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(sortingOptions.size) { index ->
                            val item = sortingOptions[index]
                            val isSelected = item.sorting == questSorting

                            FilterChip(
                                selected = isSelected,
                                onClick = { onSortingChanged(item.sorting) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                    )
                                },
                                label = { Text(item.text) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.quest_sorting_bottom_sheet_sorting_order),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        sortingDirections.forEachIndexed { index, item ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = sortingDirections.size
                                ),
                                onClick = { onSortingDirectionChanged(item.sortingDirection) },
                                selected = item.sortingDirection == sortingDirection,
                                icon = {
                                    Icon(
                                        imageVector = if (item.sortingDirection == SortingDirections.ASCENDING)
                                            Icons.Default.ArrowUpward
                                        else
                                            Icons.Default.ArrowDownward,
                                        contentDescription = null
                                    )
                                },
                            ) {
                                Text(item.text)
                            }
                        }
                    }
                }
            }

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Show completed Quests",
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Checkbox(
                        checked = showCompletedQuests,
                        onCheckedChange = onShowCompletedQuestsChanged
                    )
                }
            }
        }
    }
}

// Erweiterte Datenklasse mit Icon
data class QuestSortingItem(
    val text: String,
    val sorting: QuestSorting,
    val icon: ImageVector
)