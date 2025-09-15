package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import de.ljz.questify.core.utils.AddingDateTimeState

data class QuestDetailUiState(
    val dialogState: DialogState,
    val addingReminderDateTimeState: AddingDateTimeState,
    val addingDueDateTimeState: AddingDateTimeState,
    val isEditingQuest: Boolean,

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

sealed class DialogState {
    object None : DialogState()
    object DeleteConfirmation : DialogState()
    object CreateReminder : DialogState()
    object SetDueDate : DialogState()
    object SelectCategory : DialogState()
}