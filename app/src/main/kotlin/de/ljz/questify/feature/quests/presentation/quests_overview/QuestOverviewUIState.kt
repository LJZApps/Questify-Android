package de.ljz.questify.feature.quests.presentation.quests_overview

import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.feature.quests.domain.models.QuestEntity

data class QuestOverviewUIState(
    val questDoneDialogVisible: Boolean = false,
    val isSortingBottomSheetOpen: Boolean = false,
    val allQuestPageState: AllQuestPageState = AllQuestPageState(),
    val questDoneDialogState: QuestDoneDialogState = QuestDoneDialogState(),
)

data class AllQuestPageState(
    val quests: List<QuestEntity> = listOf(),
    val sortingDirections: SortingDirections = SortingDirections.ASCENDING,
    val showCompleted: Boolean = false,
    val sortingBy: QuestSorting = QuestSorting.ID
)

data class QuestDoneDialogState(
    val visible: Boolean = false,
    val questName: String = "",
    val xp: Int = 0,
    val points: Int = 0,
    val newLevel: Int = 0,
)

data class SortingDirectionItem(
    val text: String = "",
    val sortingDirection: SortingDirections = SortingDirections.ASCENDING
)