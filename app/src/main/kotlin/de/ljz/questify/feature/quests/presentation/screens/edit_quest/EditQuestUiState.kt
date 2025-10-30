package de.ljz.questify.feature.quests.presentation.screens.edit_quest

import de.ljz.questify.core.utils.AddingDateTimeState
import de.ljz.questify.feature.quests.data.models.SubQuestEntity

data class EditQuestUiState(
    val title: String,
    val notes: String?,
    val difficulty: Int,
    val dueDate: Long,
    val categoryId: Int?,
    val notificationTriggerTimes: List<Long>,

    val subTasks: List<SubQuestEntity>,

    val dueDateDialogVisible: Boolean,
    val addingDateTimeState: AddingDateTimeState
)
