package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import de.ljz.questify.feature.quests.data.models.QuestEntity

sealed interface QuestOverviewUiEvent {
    data class OnQuestDelete(val id: Int) : QuestOverviewUiEvent
    data class OnQuestChecked(val questEntity: QuestEntity) : QuestOverviewUiEvent

    object ToggleDrawer : QuestOverviewUiEvent
    object PerformHapticFeedback : QuestOverviewUiEvent

    data class ShowDialog(val dialogState: DialogState) : QuestOverviewUiEvent
    object CloseDialog : QuestOverviewUiEvent
    object CloseQuestDoneDialog : QuestOverviewUiEvent

    data class ShowUpdateCategoryDialog(val questCategoryEntity: QuestCategoryEntity) : QuestOverviewUiEvent

    data class OnNavigateToQuestDetailScreen(val entryId: Int) : QuestOverviewUiEvent
    data class OnNavigateToCreateQuestScreen(val categoryId: Int? = null) : QuestOverviewUiEvent
    data class OnNavigateToEditQuestScreen(val id: Int) : QuestOverviewUiEvent

    data class AddQuestCategory(val value: String): QuestOverviewUiEvent
    data class DeleteQuestCategory(val questCategoryEntity: QuestCategoryEntity) : QuestOverviewUiEvent
    data class UpdateQuestCategory(
        val questCategoryEntity: QuestCategoryEntity,
        val value: String
    ) : QuestOverviewUiEvent

    data class UpdateQuestSortingDirection(val sortingDirections: SortingDirections) : QuestOverviewUiEvent
    data class UpdateShowCompletedQuests(val value: Boolean) : QuestOverviewUiEvent
}