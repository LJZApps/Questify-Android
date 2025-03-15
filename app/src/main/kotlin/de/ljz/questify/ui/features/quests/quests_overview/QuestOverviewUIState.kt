package de.ljz.questify.ui.features.quests.quests_overview

import de.ljz.questify.core.application.QuestSorting
import de.ljz.questify.core.application.SortingDirections
import de.ljz.questify.domain.models.quests.QuestEntity

data class QuestOverviewUIState(
    val quests: List<QuestEntity> = listOf(),
    val isFastAddingFocused: Boolean = false,
    val fastAddingText: String = "",
    val questDoneDialogVisible: Boolean = false,
    val isSortingBottomSheetOpen: Boolean = false,
    val allQuestPageState: AllQuestPageState = AllQuestPageState(),
    val questDoneDialogState: QuestDoneDialogState = QuestDoneDialogState(),
    val featureSettings: FeatureSettingsState = FeatureSettingsState(),
    val userState: UserState = UserState()
)

data class UserState(
    val profilePictureUrl: String = "",
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

data class QuestSortingItem(
    val text: String = "",
    val sorting: QuestSorting = QuestSorting.ID
)

data class SortingDirectionItem(
    val text: String = "",
    val sortingDirection: SortingDirections = SortingDirections.ASCENDING
)

data class FeatureSettingsState(
    val fastQuestAddingEnabled: Boolean = true
)