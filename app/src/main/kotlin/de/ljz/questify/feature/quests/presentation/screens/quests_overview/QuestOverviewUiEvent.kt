package de.ljz.questify.feature.quests.presentation.screens.quests_overview

import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity

sealed interface QuestOverviewUiEvent {
    data class OnQuestDelete(val id: Int) : QuestOverviewUiEvent
    data class OnQuestChecked(val id: Int) : QuestOverviewUiEvent

    object ToggleDrawer : QuestOverviewUiEvent

    data class ShowDialog(val dialogState: DialogState) : QuestOverviewUiEvent
    object CloseDialog : QuestOverviewUiEvent

    data class ShowUpdateCategoryDialog(val questCategoryEntity: QuestCategoryEntity) : QuestOverviewUiEvent

    data class OnNavigateToQuestDetailScreen(val entryId: Int) : QuestOverviewUiEvent
    data class OnNavigateToCreateQuestScreen(val categoryId: Int? = null) : QuestOverviewUiEvent

    object PerformHapticFeedback : QuestOverviewUiEvent

    data class AddQuestCategory(val value: String): QuestOverviewUiEvent
    data class DeleteQuestCategory(val questCategoryEntity: QuestCategoryEntity) : QuestOverviewUiEvent
}