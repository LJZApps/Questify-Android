package de.ljz.questify.feature.quests.presentation.screens.quest_overview

import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests

data class QuestOverviewUIState(
    val dialogState: DialogState,
    val allQuestPageState: AllQuestPageState,
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
    object CreateCategory : DialogState()

    data class QuestDone(val questDoneDialogState: QuestDoneDialogState) : DialogState()
    data class UpdateCategory(val questCategoryEntity: QuestCategoryEntity) : DialogState()
    data class DeleteCategory(val questCategoryEntity: QuestCategoryEntity) : DialogState()
}