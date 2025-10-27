package de.ljz.questify.feature.quests.presentation.screens.quest_detail

import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import de.ljz.questify.feature.quests.presentation.screens.quests_overview.QuestDoneDialogState

data class QuestDetailUiState(
    val dialogState: DialogState,
    val addingReminderDateTimeState: AddingDateTimeState,
    val addingDueDateTimeState: AddingDateTimeState,
    val isEditingQuest: Boolean,
    val questEntity: QuestEntity?,

    val questId: Int,

    val questState: QuestState,
    val questDoneDialogState: QuestDoneDialogState,
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
    object QuestDone : DialogState()
    object CreateReminder : DialogState()
}