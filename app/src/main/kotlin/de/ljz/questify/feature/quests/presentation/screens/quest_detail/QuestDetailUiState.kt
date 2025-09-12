package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import de.ljz.questify.core.utils.AddingDateTimeState

data class QuestDetailUiState(
    val isAddingReminder: Boolean,
    val trophiesExpanded: Boolean,
    val addingReminderDateTimeState: AddingDateTimeState,
    val addingDueDateTimeState: AddingDateTimeState,
    val isDueDateInfoDialogVisible: Boolean,
    val isDeleteConfirmationDialogVisible: Boolean,
    val isEditingQuest: Boolean,
    val isShowingReminderBottomSheet: Boolean,
    val isDueDateSelectionDialogVisible: Boolean,
    val isSelectCategoryDialogVisible: Boolean,

    val questId: Int,

    val editQuestState: EditQuestState
)

data class EditQuestState(
    val title: String,
    val description: String,
    val difficulty: Int,
    val notificationTriggerTimes: List<Long>,
    val selectedDueDate: Long,
)