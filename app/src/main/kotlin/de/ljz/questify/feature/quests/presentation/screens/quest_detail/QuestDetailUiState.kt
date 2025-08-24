package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import de.ljz.questify.core.utils.AddingDateTimeState

data class QuestDetailUiState(
    val isAddingReminder: Boolean = false,
    val trophiesExpanded: Boolean = false,
    val addingReminderDateTimeState: AddingDateTimeState = AddingDateTimeState.NONE,
    val addingDueDateTimeState: AddingDateTimeState = AddingDateTimeState.NONE,
    val isDueDateInfoDialogVisible: Boolean = false,
    val isDeleteConfirmationDialogVisible: Boolean = false,
    val isEditingQuest: Boolean = false,
    val isShowingReminderBottomSheet: Boolean = false,
    val isDueDateSelectionDialogVisible: Boolean = false,
    val isSelectCategoryDialogVisible: Boolean = false,

    val questId: Int = 0,

    val editQuestState: EditQuestState = EditQuestState()
)

data class EditQuestState(
    val title: String = "",
    val description: String = "",
    val difficulty: Int = 0,
    val notificationTriggerTimes: List<Long> = listOf(),
    val selectedDueDate: Long = 0,
)