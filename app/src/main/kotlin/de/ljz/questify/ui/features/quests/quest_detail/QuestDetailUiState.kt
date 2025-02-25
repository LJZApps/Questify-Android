package de.ljz.questify.ui.features.quests.quest_detail

import de.ljz.questify.core.application.AddingReminderState

data class QuestDetailUiState (
    val questId: Int = 0,
    val title: String = "",
    val description: String = "",
    val difficulty: Int = 0,
    val isAddingReminder: Boolean = false,
    val selectedDueDate: Long = 0,
    val isQuestDone: Boolean = false,
    val notificationTriggerTimes: List<Long> = listOf(),
    val addingReminderState: AddingReminderState = AddingReminderState.NONE,
    val isDueDateInfoDialogVisible: Boolean = false,
    val isDeleteConfirmationDialogVisible: Boolean = false
)

