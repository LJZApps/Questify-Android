package de.ljz.questify.ui.features.quests.quests_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.ui.unit.dp
import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.ui.features.quests.quests_overview.SortingDirectionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuestSortingBottomSheet(
    onDismiss: () -> Unit,
    questSorting: QuestSorting,
    sortingDirection: SortingDirections,
    onSortingChanged: (QuestSorting) -> Unit,
    onSortingDirectionChanged: (SortingDirections) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    val sortingOptions = listOf(
        QuestSortingItem("Default", QuestSorting.ID, Icons.Default.FormatListNumbered),
        QuestSortingItem("Title", QuestSorting.TITLE, Icons.Default.Title),
        QuestSortingItem("Notes", QuestSorting.NOTES, Icons.Default.Notes),
        QuestSortingItem("Due date", QuestSorting.DUE_DATE, Icons.Default.DateRange),
        QuestSortingItem("Quest Complete", QuestSorting.DONE, Icons.Default.CheckCircle),
    )

    val sortingDirections = listOf(
        SortingDirectionItem("Ascending", SortingDirections.ASCENDING),
        SortingDirectionItem("Descending", SortingDirections.DESCENDING)
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
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
                text = "Quests sortieren",
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
                        text = "Sortieren nach",
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
                        text = "Sortierreihenfolge",
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
        }
    }
}

// Erweiterte Datenklasse mit Icon
data class QuestSortingItem(
    val text: String,
    val sorting: QuestSorting,
    val icon: ImageVector
)