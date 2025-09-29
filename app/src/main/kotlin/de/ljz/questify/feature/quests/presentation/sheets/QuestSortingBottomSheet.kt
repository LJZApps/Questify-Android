package de.ljz.questify.feature.quests.presentation.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.data.models.descriptors.SortingDirectionItem
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSection
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSwitch
import de.ljz.questify.core.utils.QuestSorting
import de.ljz.questify.core.utils.SortingDirections
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuestSortingBottomSheet(
    onDismiss: () -> Unit,
    sortingDirection: SortingDirections,
    showCompletedQuests: Boolean,
    onSortingDirectionChanged: (SortingDirections) -> Unit,
    onShowCompletedQuestsChanged: (Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val sortingDirections = listOf(
        SortingDirectionItem(
            stringResource(R.string.sorting_direction_ascending),
            SortingDirections.ASCENDING
        ),
        SortingDirectionItem(
            stringResource(R.string.sorting_direction_descending),
            SortingDirections.DESCENDING
        )
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
            onDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                val modifiers = List(sortingDirections.size) { Modifier.weight(1f) }

                sortingDirections.forEachIndexed { index, item ->
                    ToggleButton(
                        checked = item.sortingDirection == sortingDirection,
                        onCheckedChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onSortingDirectionChanged(item.sortingDirection)
                        },
                        modifier = modifiers[index].semantics {
                            role = Role.RadioButton
                        },
                        shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            sortingDirections.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = if (item.sortingDirection == SortingDirections.ASCENDING)
                                    painterResource(R.drawable.ic_arrow_upward)
                                else
                                    painterResource(R.drawable.ic_arrow_downward),
                                contentDescription = null
                            )

                            Text(item.text)
                        }
                    }
                }
            }

            ExpressiveSettingsSection {
                ExpressiveSettingsSwitch(
                    state = showCompletedQuests,
                    onCheckedChange = onShowCompletedQuestsChanged,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_check_circle_filled),
                            contentDescription = null
                        )
                    },
                    title = stringResource(R.string.filter_show_completed_quests_title)
                )
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