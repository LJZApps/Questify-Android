package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests

data class QuestOverviewUIState(
    val dialogState: DialogState,
    val allQuestPageState: AllQuestPageState,
    val questDoneDialogState: QuestDoneDialogState,
)

data class AllQuestPageState(
    val quests: List<QuestWithSubQuests>,
    val sortingDirections: SortingDirections,
    val showCompleted: Boolean,
)

data class QuestDoneDialogState(
    val questName: String,
    val xp: Int,
    val points: Int,
    val newLevel: Int,
)

sealed class DialogState {
    object None : DialogState()
    object SortingBottomSheet : DialogState()
    object QuestDone : DialogState()
    object CreateCategory : DialogState()
    object UpdateCategory : DialogState()
    object ManageCategoriesBottomSheet : DialogState()
}