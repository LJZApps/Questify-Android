package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.SubQuestEntity

data class QuestDetailUiState(
    val dialogState: DialogState,
    val addingReminderDateTimeState: AddingDateTimeState,
    val addingDueDateTimeState: AddingDateTimeState,
    val isEditingQuest: Boolean,

    val questId: Int,

    val questState: QuestState
)

data class QuestState(
    val title: String,
    val description: String,
    val difficulty: Difficulty,
    val notificationTriggerTimes: List<Long>,
    val selectedDueDate: Long,
    val done: Boolean,
    val subQuests: List<SubQuestEntity>
)

sealed class DialogState {
    object None : DialogState()
    object DeleteConfirmation : DialogState()
    object CreateReminder : DialogState()
    object SetDueDate : DialogState()
    object SelectCategory : DialogState()
}